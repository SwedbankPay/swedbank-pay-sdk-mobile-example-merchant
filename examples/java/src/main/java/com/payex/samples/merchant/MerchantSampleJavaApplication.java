package com.payex.samples.merchant;

import static com.payex.samples.merchant.Constants.ENV_VAR_SERVER_BASEURL;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MerchantSampleJavaApplication {
	private static final Logger log = LoggerFactory.getLogger(MerchantSampleJavaApplication.class);

	public static void main(String[] args) {
		log.info("Creating Merhant Sample Spring Boot application!");

		final String envValue = System.getenv(ENV_VAR_SERVER_BASEURL);
		if (envValue != null) {
			log.info("Overriding server address from environment: {}", envValue);
		}

		SpringApplication app = new SpringApplication(MerchantSampleJavaApplication.class);

		// Try to read the listen port from the environment
		final String envPort = System.getenv("PORT");
		if (envPort != null) {
			log.info("Starting to listen to env PORT {}", envPort);
			app.setDefaultProperties(Collections.singletonMap("server.port", envPort));
		}

		// Start listening to incoming connections
		app.run(args);
	}
}
