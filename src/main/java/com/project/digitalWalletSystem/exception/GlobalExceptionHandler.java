package com.project.digitalWalletSystem.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Helper method to build JSON response
    private ResponseEntity<Object> buildResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 1. Handle Customer Not Found
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Invalid email or password"); // custom message for frontend
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    }
    // 2. Handle Wallet Not Found
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFound(WalletNotFoundException ex) {
        return buildResponse("Wallet Not Found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 3. Handle Insufficient Balance
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalance(InsufficientBalanceException ex) {
        return buildResponse("Insufficient Balance", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 4. Handle Duplicate Resource (Email/Phone)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateResource(DuplicateResourceException ex) {
        Map<String, String> errors = new HashMap<>();
        String message = ex.getMessage();
        if (message.contains("Email")) errors.put("customerEmail", message);
        else if (message.contains("Phone")) errors.put("customerPhoneNumber", message);
        else errors.put("general", message);

        Map<String, Object> body = new HashMap<>();
        body.put("errors", errors);  // key must be 'errors'
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    
    // 5. Handle @Valid validation errors (POST/PUT body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("messages", fieldErrors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 6. Handle ConstraintViolationException (@PathVariable / @RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return buildResponse("Constraint Violation", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 7. Catch-all Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponse("Internal Server Error", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
