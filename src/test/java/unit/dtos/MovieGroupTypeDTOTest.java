package unit.dtos;

import br.gov.sp.fatec.dtos.MovieGroupTypeDTO;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieGroupTypeDTOTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = MovieGroupTypeDTO.class;
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }
}
