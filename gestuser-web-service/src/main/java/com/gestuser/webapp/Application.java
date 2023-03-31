package com.gestuser.webapp;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
//		SpringApplication.run(Application.class, args);
		
		SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8019"));
        app.run(args);
		
	}
	
	
	
	

}
 