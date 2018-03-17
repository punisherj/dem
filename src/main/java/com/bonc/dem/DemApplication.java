package com.bonc.dem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemApplication.class, args);
	}
}
