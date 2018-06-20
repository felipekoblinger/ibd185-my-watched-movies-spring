package integration.controllers;

import br.gov.sp.fatec.security.TokenUtil;
import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class TheMovieDatabaseControllerTest {
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void testShow() throws Exception {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);

        String token = tokenUtil.generateToken(securityAccount);

        mockMvc.perform(get("/the-movie-database/search/?term=rampage")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}
