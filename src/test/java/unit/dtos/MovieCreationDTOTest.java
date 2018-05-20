package unit.dtos;

import br.gov.sp.fatec.dtos.MovieCreationDTO;
import factories.MovieCreationDTOFactory;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.ValidationUtil;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieCreationDTOTest {
    private ValidationUtil validationUtil = new ValidationUtil();
    private MovieCreationDTO movieCreationDTO;

    @Before
    public void setUp() {
        Locale.setDefault(new Locale("en", "EN"));
        this.movieCreationDTO = MovieCreationDTOFactory.validResource();
    }

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

    /* Validations */
    @Test
    public void testValidationMovieCreationDTO() {
        assertEquals(0, validationUtil.getErrorSize(movieCreationDTO));
    }

    @Test
    public void testValidationTitle() {
        movieCreationDTO.setTitle(null);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movieCreationDTO));

        movieCreationDTO.setTitle("");
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movieCreationDTO));
    }

    @Test
    public void testValidationDate() {
        movieCreationDTO.setDate(null);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(movieCreationDTO));

        movieCreationDTO.setDate(LocalDate.now().plusDays(5));
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must be a date in the past or in the present", validationUtil.getErrorMessage(movieCreationDTO));
    }

    @Test
    public void testValidationRating() {
        movieCreationDTO.setRating(null);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(movieCreationDTO));

        movieCreationDTO.setRating(-1);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must be greater than or equal to 0", validationUtil.getErrorMessage(movieCreationDTO));

        movieCreationDTO.setRating(6);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must be less than or equal to 5", validationUtil.getErrorMessage(movieCreationDTO));
    }

    @Test
    public void testValidationType() {
        movieCreationDTO.setType(null);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(movieCreationDTO));
    }

    @Test
    public void testValidationImdbId() {
        movieCreationDTO.setImdbId(null);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movieCreationDTO));

        movieCreationDTO.setImdbId("");
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movieCreationDTO));
    }

    @Test
    public void testValidationTheMovieDatabaseId() {
        movieCreationDTO.setTheMovieDatabaseId(null);
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movieCreationDTO));

        movieCreationDTO.setTheMovieDatabaseId("");
        assertEquals(1, validationUtil.getErrorSize(movieCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movieCreationDTO));
    }
}