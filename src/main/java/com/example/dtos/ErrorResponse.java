package com.example.dtos;

import com.example.utils.ErrorCode;

/**
 * Rest response body used for all errors originating from within the server code base.
 */
public class ErrorResponse {

	private String errorId;
	private String errorMessage;
	private String innerExceptionMessage;
	
	public ErrorResponse(ErrorCode errorCode)
	{
		errorId = errorCode.getErrorId();
		errorMessage = errorCode.getMessage();
		innerExceptionMessage = null;
	}
	
	public ErrorResponse(ErrorCode errorCode, Throwable inner)
	{
		errorId = errorCode.getErrorId();
		errorMessage = errorCode.getMessage();
		innerExceptionMessage = inner.getMessage();
	}

	public String getErrorId() {
		return errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getInnerExceptionMessage() {
		return innerExceptionMessage;
	}
}
