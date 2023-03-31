package com.jwtauth.webapp.security;

import java.util.List;

import lombok.Data;

@Data
public class Utenti 
{
	private Integer id;
	private String userid;
	private String password;
	private String attivo;
	private Integer codicetessera;
	
	private List<Ruoli> roles;	
}
