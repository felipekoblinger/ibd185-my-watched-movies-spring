package integration;

import br.gov.sp.fatec.security.EntryPointUnauthorizedHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
public class EntryPointUnauthorizedHandlerTest {
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    @Test
    public void testCommence() throws IOException, AuthenticationCredentialsNotFoundException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        AuthenticationException authenticationException = new AuthenticationCredentialsNotFoundException("");

        entryPointUnauthorizedHandler.commence(mockHttpServletRequest, mockHttpServletResponse, authenticationException);

        assertEquals("Must return unauthorized status", HttpServletResponse.SC_UNAUTHORIZED,
                mockHttpServletResponse.getStatus());
    }
}
