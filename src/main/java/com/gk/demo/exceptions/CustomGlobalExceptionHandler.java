package com.gk.demo.exceptions;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomErrorDetails customErrorDetails=new CustomErrorDetails(new Date(), "from method argument not valid in CGEH", ex.getMessage());
		//return handleExceptionInternal(ex, null, headers, status, request);
		return new ResponseEntity<>(customErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomErrorDetails customErrorDetails=new CustomErrorDetails(new Date(), "fromhandleHttpRequestMethodNotSupported in CGEH-Method not allowed", ex.getMessage());
		
		return new ResponseEntity<>(customErrorDetails,HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handlerUserNameNotFoundException(UserNotFoundException ex, WebRequest request) {
		CustomErrorDetails customErrorDetails=new CustomErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		
		return new ResponseEntity<>(customErrorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handlerUserNameNotFoundException(ConstraintViolationException ex, WebRequest request) {
		CustomErrorDetails customErrorDetails=new CustomErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		
		return new ResponseEntity<>(customErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
}
