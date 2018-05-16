package integration.security.filters;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.repositories.AccountRepository;
import br.gov.sp.fatec.security.TokenUtil;
import br.gov.sp.fatec.security.filters.AuthenticationTokenFilter;
import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import ch.qos.logback.classic.Level;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import util.LogbackVerifier;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class AuthenticationTokenFilterTest {
    @Autowired
    @InjectMocks
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @InjectMocks
    private AuthenticationTokenFilter authenticationTokenFilter;

    @Mock
    private Clock clock;

    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;
    private MockFilterChain mockFilterChain;

    @Rule
    public LogbackVerifier logbackVerifier = new LogbackVerifier();

    @Value("${MWM_TOKEN_SECRET:unknown}")
    private String secret;

    @Before
    public void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
        mockFilterChain = new MockFilterChain();

        MockitoAnnotations.initMocks(this);
        when(clock.now()).thenReturn(new Date());
    }

    @Test
    public void testDoFilter() throws ServletException, IOException {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        String token = tokenUtil.generateToken(securityAccount);

        mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);
        mockHttpServletRequest.setRequestURI("/");

        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        logbackVerifier.expectMessage(Level.INFO,
                "authenticated user marionakani setting models context");
        assertEquals("Must return status code 200", 200, mockHttpServletResponse.getStatus());
    }

    @Test(expected = MalformedJwtException.class)
    public void testDoFilterWithMalformedKey() throws ServletException, IOException {
        mockHttpServletRequest.addHeader("Authorization", "Bearer 123abc");
        mockHttpServletRequest.setRequestURI("/");

        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
    }

    @Test(expected = SignatureException.class)
    public void testDoFilterWithNonServerKey() throws ServletException, IOException {
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYmMiLCJhdWRpZW5jZSI6IndlYiIsImNyZWF0ZWQiOjE1MjYxMDI1ODI5NDEsImV4cCI6MTUyNjcwNzM4Mn0.uZoPPqu9Vf_zSyaUHq0jKC6vlTKtzoWhNS5ya0t0AUlGnOtDFhhkua8UmHYZTr5VSSdYd_WlppKPhiRaTdrFVg");
        mockHttpServletRequest.setRequestURI("/");

        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
    }

    @Test
    public void testDoFilterWithExpiredKey() throws ServletException, IOException {
        when(clock.now()).thenReturn(new Date(new Date().getTime() - (86400000 * 10)));

        SecurityAccount securityAccount = (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        String token = tokenUtil.generateToken(securityAccount);

        mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);
        mockHttpServletRequest.setRequestURI("/");

        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        logbackVerifier.expectMessage(Level.WARN, "the token is expired and not valid anymore");
    }

    @Test
    public void testDoFilterWithWrongLastPasswordResetDate() throws ServletException, IOException {
        SecurityAccount securityAccount = (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        String token = tokenUtil.generateToken(securityAccount);

        Account account = accountRepository.findById(securityAccount.getId()).orElse(null);
        assertNotNull("Account not found.", account);

        account.setLastPasswordResetDate(new Date(new Date().getTime() + (1000000 * 10)));
        accountRepository.save(account);

        mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);
        mockHttpServletRequest.setRequestURI("/");

        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        logbackVerifier.expectMessage(Level.ERROR, "couldn't validate token for user marionakani");
    }

    @Test
    public void testDoFilterWithoutSecurityContextNull() throws ServletException, IOException {
        SecurityAccount securityAccount = (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        String token = tokenUtil.generateToken(securityAccount);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(securityAccount, null, securityAccount.getAuthorities());

        ReflectionTestUtils.setField(authenticationTokenFilter, "authentication", authentication);

        mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);
        mockHttpServletRequest.setRequestURI("/");

        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        logbackVerifier.expectMessage(Level.WARN, "couldn't check authentication for user null or already authenticated");
    }

    @Test
    public void testDoFilterWithNullHeader() throws ServletException, IOException {
        mockHttpServletRequest.setRequestURI("/");
        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        logbackVerifier.expectMessage(Level.WARN, "couldn't find bearer string, will ignore the header");
    }

    @Test
    public void testDoFilterWithHeaderWithoutBearer() throws ServletException, IOException {
        mockHttpServletRequest.addHeader("Authorization", "Another thing!");
        mockHttpServletRequest.setRequestURI("/");
        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        logbackVerifier.expectMessage(Level.WARN, "couldn't find bearer string, will ignore the header");
    }
}
