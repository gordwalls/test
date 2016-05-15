package com.example.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.dtos.ErrorResponse;
import com.example.utils.ErrorCode;
import com.example.utils.ErrorCodeException;
import com.example.utils.ErrorCodes;
import com.example.utils.LogUtils;

/**
 * This class is a common superclass for all controllers. It provides consistent exception handlers for all controllers.
 */
public class ExceptionHandlersForControllers {

	// Always use the logger from the subclass so that error logs from these handlers are connected to the correct controller.
	Logger log;

	protected ExceptionHandlersForControllers(Logger log) {
		super();
		this.log = log;
	}
	
	// NOTE: The order of the exception handers below is significant.  They must be ordered from more specific
	// to less specific.  Think of them as a series of catch statements.  Any exception that escapes a controller 
	// will be evaluated against these handlers in order - the first match runs.
	//
	// These handlers free the controllers from
	// boiler plate catch blocks which don't have to be repetitively added to every controller method

	/**
	 * Exception handler for business logic errors.
	 * @param request The framework request object.
	 * @param response The framework response object (under construction).
	 * @param exception The exception from the server code containing the error code.
	 * @return The error response body.  The framework places this directly into the HTTP content response.
	 */
	@ExceptionHandler(ErrorCodeException.class)
	public ErrorResponse handleErrorCode(HttpServletRequest request, HttpServletResponse response, ErrorCodeException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		
		// Log the exception
		LogUtils.logFailure(log, request, exception);
		
		// Set the status code to match the exception
		response.setStatus(errorCode.getStatusCode().value());
		
		// Create the error response body.
		ErrorResponse errorResponse = new ErrorResponse(errorCode);
		return errorResponse;
	}

	/**
	 * Exception handler for database failures. In Spring, all database failures are wrapped in DataAccessException.
	 * @param request The framework request object.
	 * @param response The framework response object (under construction).
	 * @param exception The exception from the data layer.
	 * @return The error response body.  The framework places this directly into the HTTP content response.
	 */
	@ExceptionHandler(DataAccessException.class)
	public ErrorResponse handleErrorCode(HttpServletRequest request, HttpServletResponse response, DataAccessException exception) {
		ErrorCode errorCode = ErrorCodes.DatabaseFailed;
		
		// Log the exception
		LogUtils.logFailure(log, request, exception);
		
		// Set the status code to match the exception
		response.setStatus(errorCode.getStatusCode().value());
		
		// Create the error response body.
		ErrorResponse errorResponse = new ErrorResponse(errorCode, exception);
		return errorResponse;
	}

	/**
	 * Exception handler for any other failure. 
	 * @param request The framework request object.
	 * @param response The framework response object (under construction).
	 * @param exception The unexpected exception.
	 * @return The error response body.  The framework places this directly into the HTTP content response.
	 */
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleErrorCode(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		ErrorCode errorCode = ErrorCodes.Generic500;
		
		// Log the exception
		LogUtils.logFailure(log, request, exception);
		
		// Set the status code to match the exception
		response.setStatus(errorCode.getStatusCode().value());
		
		// Create the error response body.
		ErrorResponse errorResponse = new ErrorResponse(errorCode, exception);
		return errorResponse;
	}
}
