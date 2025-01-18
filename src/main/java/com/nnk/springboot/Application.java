package com.nnk.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger log = LogManager.getLogger(Application.class);

	public static void main(String[] args) {

		log.info("Initalizing API Poseidon Capital Solution");
		SpringApplication.run(Application.class, args);

	}
}
