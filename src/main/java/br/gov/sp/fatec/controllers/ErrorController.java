package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.exceptions.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ErrorController {
    @RequestMapping(value = "/401/")
    public ResponseEntity<ApiError> error401() {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.UNAUTHORIZED);
        apiError.setMessage("The request has not been applied because it lacks valid authentication credentials for the target resource.");
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/403/")
    public ResponseEntity<ApiError> error403() {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.FORBIDDEN);
        apiError.setMessage("The server understood the request but refuses to authorize it.");
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/404/")
    public ResponseEntity<ApiError> error404() {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage("The origin server did not find a current representation for the target resource or is not willing to disclose that one exists.");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}