package com.example.utils;

import org.springframework.http.HttpStatus;

/**
 * This class contains all the error codes known to the server.
 */
public final class ErrorCodes {

	// NOTE: Please add new codes as new error conditions are coded.
	
	// NOTE: If messages are shown to the user, I18N is required here.
	
	// 400 Bad Request
	public static final ErrorCode Generic400 = new ErrorCode(HttpStatus.BAD_REQUEST, 0, "Bad Request");
	public static final ErrorCode MessageMissingInRequest = new ErrorCode(HttpStatus.BAD_REQUEST, 1, "The message text is not present in the request");
	
	// 404 Not Found
	public static final ErrorCode Generic404 = new ErrorCode(HttpStatus.NOT_FOUND, 0, "Not Found");
	public static final ErrorCode MessageNotFound = new ErrorCode(HttpStatus.NOT_FOUND, 1, "Message not found");
	
	// 409 Conflict
	public static final ErrorCode Generic409 = new ErrorCode(HttpStatus.CONFLICT, 0, "Conflict");
	public static final ErrorCode MessageAlreadyExists = new ErrorCode(HttpStatus.CONFLICT, 1, "Message already exists");
	
	// 500 Server Failed
	public static final ErrorCode Generic500 = new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, 0, "Internal Server Error");
	public static final ErrorCode DatabaseFailed = new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, 1, "Database Failed");
	
	private ErrorCodes()
	{}
}
