package com.pecheur_lover.pecheurlover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PecheurLoverApplication {
	public static void main(String[] args) {
		SpringApplication.run(PecheurLoverApplication.class, args);
	}
}
