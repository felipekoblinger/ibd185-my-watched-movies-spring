package unit.utils;

import br.gov.sp.fatec.utils.JsonIO;
import com.google.gson.JsonObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.ConnectionOptions;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class JsonIOTest {
    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, 9000);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLoad() {
        new MockServerClient("localhost", 9000)
            .when(request().withMethod("GET").withPath("/login"))
            .respond(
                response().withStatusCode(200).withBody("{'username': 'a', 'password': 'b'}")
            );
        JsonObject jsonObject = new JsonIO().load("http://localhost:9000/login");
        assertEquals("a", jsonObject.get("username").getAsString());
        assertEquals("b", jsonObject.get("password").getAsString());
    }

    @Test
    public void testLoadWithIOException() {
        new MockServerClient("localhost", 9000)
                .when(request().withPath("/test-error/"))
                .respond(
                        response().withStatusCode(500)
                );
        JsonObject jsonObject = new JsonIO().load("http://localhost:9000/test-error/");
        assertNull("IO Exception gives null object", jsonObject);
    }
}
