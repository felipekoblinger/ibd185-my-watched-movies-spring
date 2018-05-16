package unit.models.exceptions;

import br.gov.sp.fatec.exceptions.ApiSubError;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ApiSubErrorTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = ApiSubError.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }
}
