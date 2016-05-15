package com.example.utils;

import org.springframework.http.HttpStatus;

/** 
 * This class represents an error condition in the server.  Its purpose is to relate the error condition to an associated
 * http status as well as provide granularity within the http status so that clients can distinguish different conditions.
 */
public class ErrorCode {

	// The associated HTTP code.
	private HttpStatus statusCode;
	
	// A sequence number to distinguish different errors within an HTTP code.
	private int subCode;
	
	// NOTE. If this message is shown in a GUI then I18N support is needed here.
	private String message;
	
	/**
	 * Create an immutable error code.
	 * @param statusCode The associated HTTP Status code.
	 * @param subCode Finer granularity to identify what exactly is wrong.
	 * @param message A message for the error condition.
	 */
	public ErrorCode(HttpStatus statusCode, int subCode, String message) {
		this.statusCode = statusCode;
		this.subCode = subCode;
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public int getSubCode() {
		return subCode;
	}

	public String getMessage() {
		return message;
	}
	
	//In the ErrorResponse, we include both the status and subcode as an id for the error.
	public String getErrorId() {
		return statusCode.value() + "-" + subCode;
	}
}
