package com.example.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Easy log methods for the most common logging.
 */
public class LogUtils {

	/**
	 * Log the start of a REST call that doesn't have a request body.
	 * @param logger The controller's logger.
	 * @param request The framework's request object.
	 */
	public static void logStarting(Logger logger, HttpServletRequest request)
	{
		if(logger.isLoggable(Level.INFO)){
			logger.info("STARTING " + request.getMethod() + " " + request.getRequestURI());;
		}
	}
	
	/**
	 * Log the start of a REST call that does have a request body.
	 * @param logger The controller's logger.
	 * @param request The framework's request object.
	 * @param requestBody The REST call's request body. Spring guarantees that this will not be null.
	 */
	public static void logStarting(Logger logger, HttpServletRequest request, Object requestBody)
	{
		if(logger.isLoggable(Level.INFO)){
			// Convert the request body to JSON
			String requestBodyStr = convertObjectToJson(requestBody);
			logger.info("STARTING " + request.getMethod() + " " + request.getRequestURI() + requestBodyStr);;
		}
	}
	
	/**
	 * Log the successful end of a REST call.
	 * @param logger The controller's logger.
	 * @param request The framework's request object.
	 */
	public static void logStopping(Logger logger, HttpServletRequest request)
	{
		if(logger.isLoggable(Level.INFO)){
			logger.info("STOPPING " + request.getMethod() + " " + request.getRequestURI());;
		}
	}
	
	/**
	 * Logs the unsuccessful end of a REST call
	 * @param logger The controller's logger.
	 * @param request The framework's request object.
	 * @param ex The exception that aborted the rest call.
	 */
	public static void logFailure(Logger logger, HttpServletRequest request, Exception ex){
		String topMsg = "FAILED " + request.getMethod() + " " + request.getRequestURI();
		logger.log(Level.SEVERE, topMsg, ex);
	}
	
	
	/**
	 * Simple conversion to unformatted (i.e. single line) JSON string.  This is provided
	 * publicly because it is also used for testcases.
	 * @param object The object to convert.
	 * @return The object in JSON string form (unformatted - single line).
	 */
	public static String convertObjectToJson(Object object)
	{
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			return mapper.writeValueAsString(object);
		}
		catch (JsonProcessingException jpe)
		{
			// This can't happen. If the request body was accepted by the framework, it can be converted back.
			return "Object conversion to JSON failed";
		}
	}
		
	private LogUtils()
	{}
}
