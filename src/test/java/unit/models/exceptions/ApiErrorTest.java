package unit.models.exceptions;

import br.gov.sp.fatec.exceptions.ApiError;
import org.junit.Test;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import pl.pojo.tester.api.assertion.Method;

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
}
