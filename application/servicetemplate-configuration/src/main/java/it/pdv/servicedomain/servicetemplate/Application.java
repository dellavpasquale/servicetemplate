package it.pdv.servicedomain.servicetemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "it.pdv.servicedomain.servicetemplate.configuration",
		"it.pdv.servicedomain.servicetemplate.restapi.configuration" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}