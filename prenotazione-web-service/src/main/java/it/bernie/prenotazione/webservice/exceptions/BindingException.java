package it.bernie.prenotazione.webservice.exceptions;

public class BindingException extends Exception {

	private static final long serialVersionUID = -5198072630604345819L;
	
	private String messaggio;

	public BindingException() {
		super();
	}

	public BindingException(String message) {
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
