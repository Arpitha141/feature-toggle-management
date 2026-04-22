package com.example.featuretoggle.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	// Single Validation Error Handling for Reference 
	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public
	 * ResponseEntity<ErrorResponse>
	 * handleValidationExceptions(MethodArgumentNotValidException ex,
	 * HttpServletRequest request) {
	 * 
	 * String errorMessage = ex.getBindingResult().getFieldErrors().stream()
	 * .map(error -> error.getField() + ": " + error.getDefaultMessage())
	 * .collect(Collectors.joining(", "));
	 * 
	 * return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error",
	 * errorMessage, request.getRequestURI());
	 * 
	 * }
	 */
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
		// 1. Combine all field errors into a Map (Field Name -> Error Message)
				Map<String, String> validationErrors = new HashMap<>();
				ex.getBindingResult().getFieldErrors().forEach(error -> 
						validationErrors.put(error.getField(), error.getDefaultMessage()));
				
				// 2. Build a structured JSON response
				Map<String , Object> response = new LinkedHashMap<>();
				response.put("timestamp",LocalDateTime.now());
				response.put("status", HttpStatus.BAD_REQUEST.value());
				response.put("error", "Validation Error");
				response.put("messages", validationErrors); // Injects the structured map here
				response.put("path", request.getRequestURI());
				
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Malformed JSON Request");
        
        // Provide a friendly hint about the parsing error
        response.put("message", "Could not parse the request body. Ensure all fields (like Enums) have valid values.");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Argument", ex.getMessage(), request.getRequestURI());
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Data Integrity Violation", ex.getMessage(), request.getRequestURI());
    }
	
	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request.getRequestURI());
    }

	private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String error, String message, String path) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                path
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}
