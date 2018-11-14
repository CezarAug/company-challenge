package com.companytest.citydomain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CityDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityDomainApplication.class, args);
	}


}
