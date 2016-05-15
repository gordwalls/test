package com.example.dtos;

import com.example.entities.Message;

/**
 * Response body for messages - used for all REST calls on this endpoint.
 */
public class MessageResponse {
	
	private int id;
	private String message;
	private boolean palindrome;
		
	public MessageResponse(Message message) {
		super();
		this.id = message.getId();
		this.message = message.getMessage();
		this.palindrome = message.isPalindrome();
	}
	
	public int getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public boolean isPalindrome() {
		return palindrome;
	}
}
