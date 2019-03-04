package com.example.erueka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EruekaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EruekaProducerApplication.class, args);
	}

}
