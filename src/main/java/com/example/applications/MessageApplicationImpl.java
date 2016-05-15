package com.example.applications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dtos.MessageRequest;
import com.example.dtos.MessageResponse;
import com.example.entities.Message;
import com.example.repositories.MessageRepository;
import com.example.utils.ErrorCodeException;
import com.example.utils.ErrorCodes;

/**
 * This contains all the business logic implementations for messages. 
 */
@Component
public class MessageApplicationImpl implements MessageApplication {

	@Autowired 
	MessageRepository messageRepo;
	
	@Override
	public List<MessageResponse> getAllMessages() {
		Iterable<Message> messages = messageRepo.findAll();
		List<MessageResponse> result = new ArrayList<MessageResponse>();
		
		for(Message message : messages) {
			result.add(new MessageResponse(message));
		}
		return result;
	}
	
	@Override
	public MessageResponse getMessage(int id) {
		Message message = messageRepo.findOne(id);
		if( message == null) {
			throw new ErrorCodeException(ErrorCodes.MessageNotFound);
		}
		return new MessageResponse(message);
	}


	@Override
	public MessageResponse createMessage(MessageRequest request) {
		request.verify();
		
		// We calculate the palindrome state on save so that it doesn't need calculation on 
		// every subsequent response.
		boolean palindrome = detectPanindrome(request.getMessage());
		
		Message newMsg = new Message(request.getMessage(), palindrome);
		Message msgInDB = messageRepo.save(newMsg);
		return new MessageResponse(msgInDB);
	}

	@Override
	public MessageResponse deleteMessage(int id) {
		Message message = messageRepo.findOne(id);
		if( message == null) {
			throw new ErrorCodeException(ErrorCodes.MessageNotFound);
		}
		MessageResponse pendingResult = new MessageResponse(message);
		messageRepo.delete(id);
		return pendingResult;
	}
	
	private boolean detectPanindrome(String msg) {
		StringBuffer forward = new StringBuffer();
		StringBuffer reverse = new StringBuffer();
		
		// According to most conventional definitions, palindromes only apply to letters and numbers.
		// For example - A man, a plan, a canal: Panama.
		for(int i=0; i < msg.length(); i++) {
			Character c = msg.charAt(i);
			if( Character.isLetter(c) || Character.isDigit(c)){
				forward.append(c);
				reverse.insert(0, c);
			}
		}
		return forward.toString().equalsIgnoreCase(reverse.toString());
	}
}
