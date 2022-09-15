package com.self.mycollegeapp.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MycollegeAppDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycollegeAppDiscoveryApplication.class, args);
	}

}
