package com.dohyeong.eureka_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class eureka_serviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(eureka_serviceApplication.class, args);
	}

}
