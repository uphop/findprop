package com.lightson.findpropapi.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlerApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(CrawlerApplication.class, args)));
	}

}
