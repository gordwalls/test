package com.example.entities;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;

/**
 * The Message entity as it exists in the database.
 */
@Region("message")  // In Gemfire, regions are like tables.
public class Message {

	// To overcome Gemfire not having auto generated ids (that I can find). This alone prevents use in a cluster.
	// If Gemfire does not support, the real alternative is to run a persistent counter in the DB.
	// Another alternative is to use UUIDs for ids - only useful if humans never have to enter the id.
	// 
	// TODO. More research here and try to use something like @GeneratedValue as with SQL DBs.
	private static AtomicInteger COUNTER = new AtomicInteger(0);
	
	@Id 
	private Integer id;
	
	private String message;
	private boolean palindrome;
	
	@PersistenceConstructor
	public Message(String message, boolean palindrome) {
		this.id = COUNTER.incrementAndGet();
		this.message = message;
		this.palindrome = palindrome;
	}

	public int getId(){
		// Null id is not possible. Spring data will guarantee.
		return id;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isPalindrome() {
		return palindrome;
	}

	public void setPalindrome(boolean palindrome) {
		this.palindrome = palindrome;
	}
}
