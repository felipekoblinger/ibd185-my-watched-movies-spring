package unit.models;

import br.gov.sp.fatec.models.Movie;
import factories.MovieFactory;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.ValidationUtil;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class MovieTest {
    private ValidationUtil validationUtil = new ValidationUtil();
    private Movie movie;

    @Before
    public void setUp() {
        Locale.setDefault(new Locale("en", "EN"));
        this.movie = MovieFactory.validResource();
    }

    @Test
    public void testPojo() {
        final Class<?> classUnderTest = Movie.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER)
                .areWellImplemented();
    }

    /* Validations */
    @Test
    public void testValidationMovie() {
        assertEquals(0, validationUtil.getErrorSize(movie));
    }

    @Test
    public void testValidationId() {
        movie.setId(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationUuid() {
        movie.setUuid("");
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movie));

        movie.setUuid(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationTitle() {
        movie.setTitle("");
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movie));

        movie.setTitle(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationDate() {
        movie.setDate(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));

        movie.setDate(LocalDate.now().plusDays(1L));
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must be a date in the past or in the present", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationRating() {
        movie.setRating(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));

        movie.setRating(6);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must be less than or equal to 5", validationUtil.getErrorMessage(movie));

        movie.setRating(-1);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must be greater than or equal to 0", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationType() {
        movie.setType(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationTheMovieDatabaseId() {
        movie.setTheMovieDatabaseId("");
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movie));

        movie.setTheMovieDatabaseId(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be empty", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationCreatedAt() {
        movie.setCreatedAt(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationUpdatedAt() {
        movie.setUpdatedAt(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));
    }

    @Test
    public void testValidationAccount() {
        movie.setAccount(null);
        assertEquals(1, validationUtil.getErrorSize(movie));
        assertEquals("must not be null", validationUtil.getErrorMessage(movie));
    }
}