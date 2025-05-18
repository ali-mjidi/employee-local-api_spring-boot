package com.example.employeeCrud.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeRestExceptionController {

	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> exceptionHandler(
			EmployeeNotFoundException exception) {
		EmployeeErrorResponse error = new EmployeeErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage()
		);

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> exceptionHandler(Exception exception) {
		EmployeeErrorResponse error = new EmployeeErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				exception.getMessage()
		);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
