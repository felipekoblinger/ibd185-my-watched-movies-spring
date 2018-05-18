package integration.controllers;

import br.gov.sp.fatec.dtos.MovieCreationDTO;
import br.gov.sp.fatec.dtos.MovieUpdatingDTO;
import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.security.TokenUtil;
import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class MovieControllerTest {
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
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);
        String token = tokenUtil.generateToken(securityAccount);

        MovieCreationDTO movieCreationDTO = new MovieCreationDTO();
        movieCreationDTO.setTitle("Rampage");
        movieCreationDTO.setImdbId("tt2231461");
        movieCreationDTO.setTheMovieDatabaseId("427641");
        movieCreationDTO.setDate(LocalDate.of(2018, 4, 4));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String movieJson = objectMapper.writeValueAsString(movieCreationDTO);

        mockMvc.perform(post("/movies/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(movieJson))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }
    @Test
    public void testCreateWithoutPermission() throws Exception {
        MovieCreationDTO movieCreationDTO = new MovieCreationDTO();
        movieCreationDTO.setTitle("Rampage");
        movieCreationDTO.setImdbId("tt2231461");
        movieCreationDTO.setTheMovieDatabaseId("427641");
        movieCreationDTO.setDate(LocalDate.of(2018, 4, 4));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String movieJson = objectMapper.writeValueAsString(movieCreationDTO);

        mockMvc.perform(post("/movies/").content(movieJson))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void testUpdate() throws Exception {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);
        String token = tokenUtil.generateToken(securityAccount);

        MovieUpdatingDTO movieUpdatingDTO = new MovieUpdatingDTO();
        movieUpdatingDTO.setTitle("Rampage 2");
        movieUpdatingDTO.setTheMovieDatabaseId("427643");
        movieUpdatingDTO.setDate(LocalDate.of(2018, 5, 5));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String movieJson = objectMapper.writeValueAsString(movieUpdatingDTO);

        mockMvc.perform(put("/movies/a7b5fa22-4903-4059-948b-1790088f16ed/")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(movieJson))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
    @Test
    public void testUpdateWithoutPermission() throws Exception {
        MovieUpdatingDTO movieUpdatingDTO = new MovieUpdatingDTO();
        movieUpdatingDTO.setTitle("Rampage 2");
        movieUpdatingDTO.setImdbId("tt2231462");
        movieUpdatingDTO.setTheMovieDatabaseId("427643");
        movieUpdatingDTO.setDate(LocalDate.of(2018, 5, 5));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String movieJson = objectMapper.writeValueAsString(movieUpdatingDTO);

        mockMvc.perform(put("/movies/a7b5fa22-4903-4059-948b-1790088f16ed/")
                .contentType(MediaType.APPLICATION_JSON).content(movieJson))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }


    @Test
    public void testIndex() throws Exception {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);

        String token = tokenUtil.generateToken(securityAccount);
        mockMvc.perform(get("/movies/")
                    .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)))
        ;
    }
    @Test
    public void testIndexWithoutPermission() throws Exception {
        mockMvc.perform(get("/movies/"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void testShow() throws Exception {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);

        String token = tokenUtil.generateToken(securityAccount);

        mockMvc.perform(get("/movies/a7b5fa22-4903-4059-948b-1790088f16ed")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", equalTo("a7b5fa22-4903-4059-948b-1790088f16ed")))
        ;
    }
    @Test
    public void testShowWithoutPermission() throws Exception {
        mockMvc.perform(get("/movies/a7b5fa22-4903-4059-948b-1790088f16ed"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void testDestroy() throws Exception {
        SecurityAccount securityAccount =
                (SecurityAccount) userDetailsService.loadUserByUsername("marionakani");
        assertNotNull("Account not found", securityAccount);

        String token = tokenUtil.generateToken(securityAccount);

        mockMvc.perform(delete("/movies/a7b5fa22-4903-4059-948b-1790088f16ed")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
    @Test
    public void testDestroyWithoutPermission() throws Exception {
        mockMvc.perform(delete("/movies/a7b5fa22-4903-4059-948b-1790088f16ed"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }



}
