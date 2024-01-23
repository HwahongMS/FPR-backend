package com.fpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FprApplication {

	public static void main(String[] args) {
		SpringApplication.run(FprApplication.class, args);
	}

}
