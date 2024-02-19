package com.dohyeong.activity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class User_serviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(User_serviceApplication.class, args);
	}

}
