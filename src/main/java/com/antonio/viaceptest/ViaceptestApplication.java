package com.antonio.viaceptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ViaceptestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViaceptestApplication.class, args);
	}

}
