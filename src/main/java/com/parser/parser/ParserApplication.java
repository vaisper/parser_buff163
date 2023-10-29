package com.parser.parser;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class ParserApplication {

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
	}

	@PostConstruct
	private void logInitialization() {
		System.out.println("                                                           ");
		System.out.println("***********************************************************");
		System.out.println("ACTIVE PROFILE: " + Arrays.toString(env.getActiveProfiles()));
		System.out.println("***********************************************************");
		System.out.println("                                                           ");
	}

}
