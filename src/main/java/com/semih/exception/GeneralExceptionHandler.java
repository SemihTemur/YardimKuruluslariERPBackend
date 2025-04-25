package com.semih.exception;

import com.semih.dto.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
