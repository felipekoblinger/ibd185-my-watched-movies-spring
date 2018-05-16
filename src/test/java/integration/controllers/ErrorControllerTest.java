package integration.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
@WebAppConfiguration
public class ErrorControllerTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
    }

    @Test
    public void testError401() throws Exception {
        mockMvc.perform(get("/401/"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.error.status", is("UNAUTHORIZED")));
    }

    @Test
    public void testError403() throws Exception {
        mockMvc.perform(get("/403/"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.status", is("FORBIDDEN")));
    }

    @Test
    public void testError404() throws Exception {
        mockMvc.perform(get("/404/"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.status", is("NOT_FOUND")));
    }
}
