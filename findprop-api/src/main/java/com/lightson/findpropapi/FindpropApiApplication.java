package com.lightson.findpropapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FindpropApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindpropApiApplication.class, args);
	}

}
