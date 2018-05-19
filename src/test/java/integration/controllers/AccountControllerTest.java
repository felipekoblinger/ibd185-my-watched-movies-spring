package integration.controllers;

import br.gov.sp.fatec.dtos.AccountCreationDTO;
import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.security.TokenUtil;
import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import br.gov.sp.fatec.services.AccountService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import factories.AccountCreationDTOFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;

import java.time.LocalDate;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
@WebAppConfiguration
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class AccountControllerTest {
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
    public void testCreate() throws Exception {
        AccountCreationDTO accountCreationDTO = AccountCreationDTOFactory.validResource();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String accountJson = objectMapper.writeValueAsString(accountCreationDTO);

        mockMvc.perform(post("/accounts/")
                .contentType(MediaType.APPLICATION_JSON).content(accountJson))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void testMe() throws Exception {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);

        String token = tokenUtil.generateToken(securityAccount);
        mockMvc.perform(get("/accounts/me/")
                    .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", equalTo("marionakani")))
                .andExpect(jsonPath("$.email", equalTo("marionakani@test.com")))
                .andExpect(jsonPath("$.name", equalTo("Mario Nakani")))
                .andExpect(jsonPath("$.birthday", equalTo("20-01-1990")))
        ;
    }
}
