package com.example.dtos;

import com.example.utils.ErrorCodeException;
import com.example.utils.ErrorCodes;

/**
 * Request body for messages - generally used for POST, PATCH, and/or PUT but in this case only used for POST.
 */
public class MessageRequest {

	// NOTE. If a PATCH or PUT Rest call is added for messages, this body will need an id field.
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
	/**
	 * Verifies the request body in isolation. Is the request valid without looking at an other factor.
	 * The method either returns or throws an exception.
	 */
	public void verify(){
		if(message == null) {
			throw new ErrorCodeException(ErrorCodes.MessageMissingInRequest);
		}
	}
}
