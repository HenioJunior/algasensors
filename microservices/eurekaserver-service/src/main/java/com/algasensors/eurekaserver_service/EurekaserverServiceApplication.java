package com.algasensors.eurekaserver_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverServiceApplication.class, args);
	}

}
