package unit.dtos;

import br.gov.sp.fatec.dtos.MovieUpdatingDTO;
import factories.MovieCreationDTOFactory;
import factories.MovieUpdatingDTOFactory;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.ValidationUtil;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieUpdatingDTOTest {
    private ValidationUtil validationUtil = new ValidationUtil();
    private MovieUpdatingDTO movieUpdatingDTO;

    @Before
    public void setUp() {
        Locale.setDefault(new Locale("en", "EN"));
        this.movieUpdatingDTO = MovieUpdatingDTOFactory.validResource();
    }

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

    /* Validations */
    @Test
    public void testValidationDate() {
        movieUpdatingDTO.setDate(null);
        assertEquals(0, validationUtil.getErrorSize(movieUpdatingDTO));
    }

    @Test
    public void testValidationRating() {
        movieUpdatingDTO.setRating(null);
        assertEquals(0, validationUtil.getErrorSize(movieUpdatingDTO));
    }
}