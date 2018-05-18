package unit.dtos;

import br.gov.sp.fatec.dtos.MovieUpdatingDTO;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieUpdatingDTOTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = MovieUpdatingDTO.class;
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}