package integration.services;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import br.gov.sp.fatec.services.AccountService;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testSave() {
        Account account = new Account();
        account.setUsername(" Accountprimary ");
        account.setName(" Account Primary ");
        account.setEmail("accountprimary@test.com");
        account.setPassword("12345678");
        accountService.create(account);

        assertNotNull("Account not saved.", account.getId());
        assertTrue("BCrypt is wrong.",
                BCrypt.checkpw("12345678", account.getPassword()));
        assertEquals("Account username not trimmed or lowered case.",
                "accountprimary", account.getUsername());
        assertEquals("Account name not trimmed.", "Account Primary",
                account.getName());
    }

    @Test
    @WithMockUser(authorities = { "ROLE_COMMON" }   )
    public void testFindById() {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername("marionakani");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        Account account = accountService.findById(1L);
        assertNotNull("Account not found.", account);
    }
}
