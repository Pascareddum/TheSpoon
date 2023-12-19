package it.unisa.thespoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TheSpoonApplication {

	public static void main(String[] args) {
 		SpringApplication.run(TheSpoonApplication.class, args);
	}

}
