package unit.dtos;

import br.gov.sp.fatec.dtos.MovieCreationDTO;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieCreationDTOTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = MovieCreationDTO.class;
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}