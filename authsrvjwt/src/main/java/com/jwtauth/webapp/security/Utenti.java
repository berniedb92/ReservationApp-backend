package com.jwtauth.webapp.security;

import java.util.List;

import lombok.Data;

@Data
public class Utenti 
{
	private String id;
	private String userId;
	private String password;
	private String attivo;
	
	private List<String> ruoli;	
}
