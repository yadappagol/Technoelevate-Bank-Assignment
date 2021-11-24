package com.example.demo.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exception.AccountHolderException;
import com.example.demo.exception.AdminException;
import com.example.demo.exception.ErrorMessage;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AdminException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage adminNotFound(AdminException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "BAD REQUEST", exception.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage badRequest(Exception exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), "NOT FOUND", exception.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage internalServerError(Exception exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), "INTERNAL SERVER ERROR",
				exception.getMessage(), request.getDescription(false));
	}

	@ExceptionHandler(AccountHolderException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage accountHolderNotFound(AccountHolderException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "BAD REQUEST", exception.getMessage(),
				request.getDescription(false));
	}

}
