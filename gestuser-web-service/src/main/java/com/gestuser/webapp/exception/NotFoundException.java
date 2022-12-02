package com.gestuser.webapp.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -7168287271375943562L;
	
	private String messaggio = "Elemento Ricercato Non Trovato!";

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
		this.messaggio = message;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}


}
