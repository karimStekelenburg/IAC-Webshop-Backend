package com.ElegantDevelopment.iacWebshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IacWebshopApplication {

	public static void main(String[] args) {

		SpringApplication.run(IacWebshopApplication.class, args);
	}
}
