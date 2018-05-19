package unit.models.exceptions;

import br.gov.sp.fatec.exceptions.ApiError;
import br.gov.sp.fatec.models.Movie;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import pl.pojo.tester.api.assertion.Method;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ApiErrorTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = ApiError.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.CONSTRUCTOR)
                .areWellImplemented();
    }

    @Test
    public void testAddValidationErrorsWithFieldError() {
        List<FieldError> fieldErrors = new ArrayList<>();
        FieldError fieldError = new FieldError("testObjectName", "testField", "testDefaultMessage");
        fieldErrors.add(fieldError);
        ApiError apiError = new ApiError();
        apiError.addValidationErrors(fieldErrors);
        assertTrue(apiError.getSubErrors().size() > 0);
    }

    @Test
    public void testAddValidationErrorWithObjectError() {
        List<ObjectError> objectErrors = new ArrayList<>();
        ObjectError objectError = new ObjectError("testObjectName", "testDefaultMessage");
        objectErrors.add(objectError);
        ApiError apiError = new ApiError();
        apiError.addValidationError(objectErrors);
        assertTrue(apiError.getSubErrors().size() > 0);
    }

    @Test
    public void testAddValidationErrorsWithConstraintViolation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Movie movie = new Movie();
        ConstraintViolationException constraintViolationException = new ConstraintViolationException
                (validator.validate(movie));

        ApiError apiError = new ApiError();
        apiError.addValidationErrors(constraintViolationException.getConstraintViolations());
        assertTrue(apiError.getSubErrors().size() > 0);
    }
}
