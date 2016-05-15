package com.example;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.applications.MessageApplication;
import com.example.controllers.MessageController;
import com.example.dtos.MessageRequest;
import com.example.utils.ErrorCode;
import com.example.utils.ErrorCodes;
import com.example.utils.LogUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestApplication.class, MessageController.class, MessageApplication.class})
@WebAppConfiguration
public class MessageTests {

	private MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext wac;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	// Its easier to test all calls in one test as a sequence.
	@Test
	public void allCallsSuccessTest() throws Exception {
		
		// Get empty list
		mockMvc.perform(get("/message"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
		
		// Add a message - no palindrome
		String msg1 = "Mary had a little lamb"; 
		MessageRequest req1 = new MessageRequest();
		req1.setMessage(msg1);
		String req1Json = LogUtils.convertObjectToJson(req1);
		
		ResultActions ra = mockMvc.perform(post("/message")
				.contentType(MediaType.APPLICATION_JSON)
				.content(req1Json));
		checkSingleMessage(ra, 1, msg1, false);
		
		// Add a second message - palindrome
		String msg2 = "A man, a plan, a cat, a ham, a yak, a yam, a hat, a canal-Panama!"; 
		MessageRequest req2 = new MessageRequest();
		req2.setMessage(msg2);
		String req2Json = LogUtils.convertObjectToJson(req2);
		
		ra = mockMvc.perform(post("/message")
				.contentType(MediaType.APPLICATION_JSON)
				.content(req2Json));
		checkSingleMessage(ra, 2, msg2, true);
		
		// Add the same message twice.
		ra = mockMvc.perform(post("/message")
				.contentType(MediaType.APPLICATION_JSON)
				.content(req1Json));
		checkSingleMessage(ra, 3, msg1, false);
		
		// Get all the messages
		// Checking lists is always troublesome. I admit that I don't test here if the field are aligned - but that will be confirmed later.
		mockMvc.perform(get("/message"))
			.andExpect(status().isOk())
		    .andExpect(jsonPath("$", hasSize(3)))
		    .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2, 3)))
		    .andExpect(jsonPath("$[*].message", containsInAnyOrder(msg1, msg2, msg1)))
	        .andExpect(jsonPath("$[*].palindrome", containsInAnyOrder(false, true, false)));
		
		// Get the messages individually
		ra = mockMvc.perform(get("/message/1"));
		checkSingleMessage(ra, 1, msg1, false);
	
		ra = mockMvc.perform(get("/message/2"));
		checkSingleMessage(ra, 2, msg2, true);
		
		ra = mockMvc.perform(get("/message/3"));
		checkSingleMessage(ra, 3, msg1, false);
		
		// Delete a message and verify it is gone.
		ra = mockMvc.perform(delete("/message/1"));
		checkSingleMessage(ra, 1, msg1, false);
		
		mockMvc.perform(get("/message"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[*].id", containsInAnyOrder(2, 3)));
	}

	@Test
	public void getByBadIdTest() throws Exception {
		ResultActions ra = mockMvc.perform(get("/message/10"));
		checkError(ra, ErrorCodes.MessageNotFound);
	}
	
	@Test
	public void deleteByBadIdTest() throws Exception {
		ResultActions ra = mockMvc.perform(delete("/message/10"));
		checkError(ra, ErrorCodes.MessageNotFound);
	}

	@Test
	public void createByBadRequestTest() throws Exception {
		MessageRequest req1 = new MessageRequest();
		req1.setMessage(null);
		String req1Json = LogUtils.convertObjectToJson(req1);
		
		ResultActions ra = mockMvc.perform(post("/message")
				.contentType(MediaType.APPLICATION_JSON)
				.content(req1Json));
		checkError(ra, ErrorCodes.MessageMissingInRequest);
	}

	// TODO Test for unexpected expection in the server.  Need Mockito and Spring Test to work properly together.
	
	// TODO Test for database failure in the server. Need Mockito and Spring Test to work properly together.
	
	private void checkSingleMessage(ResultActions ra, int expectedId, String expectedMsg, boolean expectedPalindrome) throws Exception
	{
		ra.andExpect(status().isOk());
		ra.andExpect(jsonPath("$.id", is(expectedId)));
		ra.andExpect(jsonPath("$.message", is(expectedMsg)));
		ra.andExpect(jsonPath("$.palindrome", is(expectedPalindrome)));
	}
	
	static void checkError(ResultActions ra, ErrorCode errorCode) throws Exception {
		ra.andExpect(status().is(errorCode.getStatusCode().value()));
		ra.andExpect(jsonPath("$.errorId", is(errorCode.getErrorId())));
		ra.andExpect(jsonPath("$.errorMessage", is(errorCode.getMessage())));
	}
}
