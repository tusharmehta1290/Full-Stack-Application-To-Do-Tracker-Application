package com.niit.WelcomeMailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// enabling the eureka client

@SpringBootApplication
@EnableDiscoveryClient
public class WelcomeMailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelcomeMailServiceApplication.class, args);
	}

}
