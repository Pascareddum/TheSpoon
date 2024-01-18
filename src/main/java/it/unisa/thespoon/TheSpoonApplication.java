package it.unisa.thespoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TheSpoonApplication {

	public static void main(String[] args) {
 		SpringApplication.run(TheSpoonApplication.class, args);
	}

}
