package com.gestuser.webapp.exception;

import java.util.Date;

import lombok.Data;


@Data
public class ErrorResponse {
	
	private Date data = new Date();
	private int code;
	private String message;
	
}
