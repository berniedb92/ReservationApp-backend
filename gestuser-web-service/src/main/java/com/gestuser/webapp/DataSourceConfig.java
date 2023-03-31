package com.gestuser.webapp;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Bean
	public DataSource getDataSource() {
	   return DataSourceBuilder.create()
	     .driverClassName("org.postgresql.Driver")
	     .url("jdbc:postgresql://localhost:5432/reservation")
	     .username("postgres")
	     .password("foxmulder")
	     .build();	
	}
    
}


