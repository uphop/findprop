package com.lightson.findpropapi.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoaderApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(LoaderApplication.class, args)));
	}

}
