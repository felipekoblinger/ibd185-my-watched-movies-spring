package integration.exceptions;

import br.gov.sp.fatec.exceptions.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMethodArgumentNotValidException() {
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(new FieldError("station", "name", "should not be empty")));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.methodArgumentNotValidException
                (methodArgumentNotValidException);
        assertEquals("Must return bad request", HttpStatus.BAD_REQUEST,
                responseEntity.getStatusCode());
    }

    @Test
    public void testHandleDataIntegrityViolation() {
        ResponseEntity<Object> responseEntity = globalExceptionHandler
                .handleDataIntegrityViolation();
        assertEquals("Must return conflict", HttpStatus.CONFLICT,
                responseEntity.getStatusCode());
    }

    @Test
    public void testHandleAuthenticationException() {
        AuthenticationException authenticationException = mock(AuthenticationException.class);
        when(authenticationException.getMessage()).thenReturn("mock");
        ResponseEntity<Object> responseEntity = globalExceptionHandler
                .handleAuthenticationException(authenticationException);
        assertEquals("Must return unauthorized", HttpStatus.UNAUTHORIZED,
                responseEntity.getStatusCode());
    }
}
