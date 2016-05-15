package com.example;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import com.example.entities.Message;
import com.gemstone.gemfire.cache.GemFireCache;


/*
 * This class is the main for the entire server.  Its primary role is to initialize Spring, including the embedded
 * container that allows this to be started as a main (instead of using a WAR). It also provides all the 
 * beans needed to configure the Gemfire database.
 */
@SpringBootApplication
@EnableGemfireRepositories
public class TestApplication {

	@Bean
	Properties gemfireProperties() {
		Properties gemfireProperties = new Properties();
		gemfireProperties.setProperty("name", "TestGemFireRestApplication");
		gemfireProperties.setProperty("mcast-port", "0");
		gemfireProperties.setProperty("log-level", "warning"); // Changed from "config".  Way too verbose.
		return gemfireProperties;
	}

	@Bean
	CacheFactoryBean gemfireCache() {
		CacheFactoryBean gemfireCache = new CacheFactoryBean();
		gemfireCache.setClose(true);
		gemfireCache.setProperties(gemfireProperties());
		return gemfireCache;
	}

	@Bean
	LocalRegionFactoryBean<String, Message> messageRegion(GemFireCache cache) {
		LocalRegionFactoryBean<String, Message> messageRegion = new LocalRegionFactoryBean<>();
		messageRegion.setCache(cache);
		messageRegion.setClose(false);
		messageRegion.setName("message");
		messageRegion.setPersistent(false);
		return messageRegion;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}
