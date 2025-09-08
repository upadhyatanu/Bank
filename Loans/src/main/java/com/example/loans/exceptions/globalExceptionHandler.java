package com.example.loans.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.loans.dto.errorResponseDto;

@ControllerAdvice
public class globalExceptionHandler extends ResponseEntityExceptionHandler {
	
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	        Map<String, String> validationErrors = new HashMap<>();
	        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

	        validationErrorList.forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String validationMsg = error.getDefaultMessage();
	            validationErrors.put(fieldName, validationMsg);
	        });
	        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	    }

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<errorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
	                                                                            WebRequest webRequest) {
	        errorResponseDto errorResponseDTO = new errorResponseDto(
	                webRequest.getDescription(false),
	                HttpStatus.NOT_FOUND,
	                exception.getMessage(),
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(LoanAlreadyExistsException.class)
	    public ResponseEntity<errorResponseDto> handleLoanAlreadyExistsException(LoanAlreadyExistsException exception,
	                                                                             WebRequest webRequest){
	        errorResponseDto errorResponseDTO = new errorResponseDto(
	                webRequest.getDescription(false),
	                HttpStatus.BAD_REQUEST,
	                exception.getMessage(),
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<errorResponseDto> handleGlobalException(Exception exception,
	                                                                  WebRequest webRequest) {
	        errorResponseDto errorResponseDTO = new errorResponseDto(
	                webRequest.getDescription(false),
	                HttpStatus.INTERNAL_SERVER_ERROR,
	                exception.getMessage(),
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
