package com.example.utils;

/**
 * This exception transports an error code from wherever it occurs to the exception handlers at the top of
 * the server. All methods in the server are able to throw this exception.
 */
// NOTE: I know this will generate a discussion on checked vs. unchecked exceptions. Early in my java years,
// I believed in checked exceptions but after several years using C#, I was convinced by that community's
// philosophy that checked exceptions are not useful.  Spring also conforms to this philosophy - see the
// common root to all Spring Exceptions: NestedRuntimeExeption.
public class ErrorCodeException extends RuntimeException {

	private static final long serialVersionUID = 3456664350298417155L;
	
	private ErrorCode errorCode;

	public ErrorCodeException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
