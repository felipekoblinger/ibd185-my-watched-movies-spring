package utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationUtil {
    public int getErrorSize(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = getErrorObject(object);
        return constraintViolations.size();
    }

    public Set<ConstraintViolation<Object>> getErrorObject(Object object) {
        return getValidator().validate(object);
    }

    public Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public String getErrorMessage(Object object) {
        if (getErrorObject(object).iterator().hasNext()) {
            return getErrorObject(object).iterator().next().getMessage();
        }
        return "is a valid object";
    }
}
