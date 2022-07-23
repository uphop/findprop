package com.lightson.findpropapi.api;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.lightson.findpropapi.FindpropApiApplication;

public class FindPropApiServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FindpropApiApplication.class);
	}

}
