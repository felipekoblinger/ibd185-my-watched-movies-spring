package unit.dtos;

import br.gov.sp.fatec.dtos.MovieGroupMonthlyDTO;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieGroupMonthlyDTOTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = MovieGroupMonthlyDTO.class;
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }
}
