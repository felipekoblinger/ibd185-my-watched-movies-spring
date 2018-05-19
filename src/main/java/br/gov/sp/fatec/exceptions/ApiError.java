package br.gov.sp.fatec.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonTypeName("error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ApiError {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    @JsonIgnore
    private String debugMessage;

    private List<ApiSubError> subErrors = new ArrayList<>();

    private void addSubError(ApiSubError subError) {
        subErrors.add(subError);
    }
    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiSubError(object, field, rejectedValue, message));
    }
    private void addValidationError(String object, String message) {
        addSubError(new ApiSubError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }
    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }
    public void addValidationError(List<ObjectError> objectErrors) {
        objectErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> constraintViolation) {
        this.addValidationError(
                constraintViolation.getRootBeanClass().getSimpleName(),
                ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
    }
    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}
