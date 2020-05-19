package com.ravi.marvel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class MarvelApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarvelApiApplication.class, args);
	}
}
