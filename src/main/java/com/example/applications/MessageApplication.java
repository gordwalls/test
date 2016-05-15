package com.example.applications;

import java.util.List;

import com.example.dtos.MessageRequest;
import com.example.dtos.MessageResponse;

/**
 * This represents all the operations that are possible on Messages.
 */
// NOTE that this interface is not strickly needed - injection will work without it. However, it will make Mocking
// easier in testcases if that is requires.
public interface MessageApplication {

	/**
	 * Gets all messages from the database. 
	 * @return The list of messages. If no messages exist, an empty list is returned.
	 */
	public List<MessageResponse> getAllMessages();
	
	/**
	 * Get a message from the database.
	 * @param id The id of the message to retrieve. Exception results if the id does not exist.
	 * @return The message associated with the id.
	 */
	public MessageResponse getMessage(int id);
	
	/**
	 * Create a new message in the database.
	 * @param request The message string to add. Exception results if anything is wrong with the request.
	 * @return The newly created message including its id and palindrome state.
	 */
	public MessageResponse createMessage(MessageRequest request);
	
	/**
	 * Delete a message from the database.
	 * @param id The id of the message to delete. Exception results if the id does not exist.
	 * @return The message that was just deleted.
	 */
	public MessageResponse deleteMessage(int id);
}
