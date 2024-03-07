package com.gxlpes.auth.api.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuthApiTesting {

	public static void main(String[] args) {
		SpringApplication.run(AuthApiTesting.class, args);
	}

}
