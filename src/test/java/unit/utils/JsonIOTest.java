package unit.utils;

import br.gov.sp.fatec.utils.JsonIO;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
                response().withStatusCode(200)
                          .withBody("{\"username\": \"a\", \"password\": \"b\"}")
            );
        JsonNode jsonObject = new JsonIO().load("http://localhost:9000/login");
        System.out.println(jsonObject);
        assertEquals("a", jsonObject.get("username").asText());
        assertEquals("b", jsonObject.get("password").asText());
    }

    @Test
    public void testLoadWithIOException() {
        new MockServerClient("localhost", 9000)
                .when(request().withPath("/test-error/"))
                .respond(
                        response().withStatusCode(500)
                );
        JsonNode jsonObject = new JsonIO().load("http://localhost:9000/test-error/");
        assertNull("IO Exception gives null object", jsonObject);
    }
}
