package com.example.UserAuthenticationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


// enabling discovery client for Eureka server
// enabling Feign Clients for microservice communication

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserAuthenticationServiceApplication
{

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationServiceApplication.class, args);
	}

}
