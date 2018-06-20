package unit.models.security;

import br.gov.sp.fatec.security.models.AuthenticationResponse;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AuthenticationResponseTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = AuthenticationResponse.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER)
                .areWellImplemented();
    }
}
