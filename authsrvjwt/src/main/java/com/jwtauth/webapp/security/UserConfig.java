package com.jwtauth.webapp.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component 
@ConfigurationProperties("gestuser")
@Data
public class UserConfig 
{
	private String srvUrl;
	private String userid;
	private String password;
}
