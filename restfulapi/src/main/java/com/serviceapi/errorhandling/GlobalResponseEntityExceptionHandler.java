package com.serviceapi.errorhandling;


import com.serviceapi.exceptions.checked.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<RestResponse> handleUnknownException(ApplicationException ex, WebRequest request) {
        return new ResponseEntity<>(new RestResponse<>(Boolean.FALSE, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }
}
