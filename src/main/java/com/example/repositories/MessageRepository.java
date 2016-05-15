package com.example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.entities.Message;

/**
 * Database repo for Messages. 
 */
public interface MessageRepository extends CrudRepository<Message, Integer> {
	
	// No special access methods required.  The super-interface contains all we need.
	
	// NOTE.  You might ask where the impl for this interface is?  There is none.  Spring Data will generate 
	// the impl at runtime. 
}
