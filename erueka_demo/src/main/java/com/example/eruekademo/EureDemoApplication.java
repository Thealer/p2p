package com.example.eruekademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EureDemoApplication.class, args);
	}

}
