package com.gxlpes.poc.auth.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PocAuthApi {

	public static void main(String[] args) {
		SpringApplication.run(PocAuthApi.class, args);
	}

}
