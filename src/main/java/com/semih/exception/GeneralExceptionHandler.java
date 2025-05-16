package com.semih.exception;

import com.semih.dto.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestResponse<String>> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<RestResponse<String>> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<RestResponse<String>> handleInsufficientInventoryException(InsufficientInventoryException ex) {
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeStockException.class)
    public ResponseEntity<RestResponse<String>> handleNegativeStockException(NegativeStockException ex) {
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<RestResponse<String>> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<RestResponse<String>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), HttpStatus.UNAUTHORIZED); // 401 dönüyoruz
    }


}
