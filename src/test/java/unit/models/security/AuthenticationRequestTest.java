package unit.models.security;

import br.gov.sp.fatec.security.models.AuthenticationRequest;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AuthenticationRequestTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = AuthenticationRequest.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }
}
