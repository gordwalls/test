package com.example.controllers;

import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.applications.MessageApplication;
import com.example.dtos.MessageRequest;
import com.example.dtos.MessageResponse;
import com.example.utils.LogUtils;

/**
 * This REST controller handles all requests for the message endpoint.
 */
@RestController
public class MessageController extends ExceptionHandlersForControllers{

	private static Logger log = Logger.getLogger(MessageController.class.getName());
	
	@Autowired
	MessageApplication messageApplication;
	
	public MessageController() {
		super(log);
	}
	
	/**
	 * This controller method handles the get all messages requests.
	 * @param request The framework request object (injected).
	 * @return A list of all the Messages (formatted for response).
	 */
	@RequestMapping(value="/message", method=RequestMethod.GET)
	Collection<MessageResponse> GetMessages(HttpServletRequest request)
	{
		// Log entrance
		LogUtils.logStarting(log, request);
		
		Collection<MessageResponse> response = messageApplication.getAllMessages();
		
		// Log successful exit
		LogUtils.logStopping(log, request);		
		return response;
	}

	/**
	 * This controller method handles the get one message requests.
	 * @param id The id of the message to get. If the id is not found, an exception results.
	 * @param request The framework request object (injected).
	 * @return The message that matches the id.
	 */
	@RequestMapping(value="/message/{id}", method=RequestMethod.GET)
	MessageResponse GetMessage(@PathVariable(value="id") int id, HttpServletRequest request)
	{
		// Log entrance
		LogUtils.logStarting(log, request);
		
		MessageResponse responseBody = messageApplication.getMessage(id);
		
		// Log successful exit
		LogUtils.logStopping(log, request);
		return responseBody;
	}
	
	/**
	 * This controller method handles the creation of a message.
	 * @param requestBody The content body in the request that contains the new message. If this object will cause exceptions if the message is missing.
	 * @param request The framework request object (injected).
	 * @return The new message in response format.
	 */
	@RequestMapping(value="/message", method=RequestMethod.POST)
	MessageResponse CreateMessage(@RequestBody MessageRequest requestBody, HttpServletRequest request)
	{
		// Log entrance
		LogUtils.logStarting(log, request, requestBody);
		
		// Spring guarantees that the request body will not be null - even if the JSON in the request as syntax errors.
		MessageResponse responseBody = messageApplication.createMessage(requestBody);
		
		// Log successful exit
		LogUtils.logStopping(log, request);
		return responseBody;
	}
	
	/**
	 * This controller method handles the deletion of a message.
	 * @param id The id of the message to delete.  If the id is not found, an exception results.
	 * @param request The framework request object (injected).
	 * @return The message that was just deleted in response format.
	 */
	@RequestMapping(value="/message/{id}", method=RequestMethod.DELETE)
	MessageResponse DeleteMessage(@PathVariable(value="id") int id, HttpServletRequest request)
	{
		// Log entrance
		LogUtils.logStarting(log, request);
		
		MessageResponse responseBody = messageApplication.deleteMessage(id);
		
		// Log successful exit
		LogUtils.logStopping(log, request);
		return responseBody;
	}
}
