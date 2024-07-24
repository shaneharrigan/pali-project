package com.example.PaliProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * <p>
 * This class is responsible for handling exceptions thrown by controllers throughout the application.
 * It provides a centralized way to handle specific exceptions and return appropriate HTTP responses.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link IllegalArgumentException} thrown by any controller.
     * <p>
     * When an {@code IllegalArgumentException} is thrown, this method returns a response with HTTP status
     * {@link HttpStatus#BAD_REQUEST} (400) and includes the exception's message in the response body.
     * </p>
     *
     * @param ex the {@code IllegalArgumentException} that was thrown
     * @return a {@link ResponseEntity} containing the exception message and a {@link HttpStatus#BAD_REQUEST}
     *         status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
