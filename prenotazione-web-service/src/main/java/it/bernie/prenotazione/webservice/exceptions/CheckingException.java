package it.bernie.prenotazione.webservice.exceptions;

public class CheckingException extends Exception {

private static final long serialVersionUID = 1L;
	
	private String messaggio;
	
	public CheckingException()
	{
		super();
	}
	
	public CheckingException(String messaggio)
	{
		super(messaggio);
		this.messaggio = messaggio;
	}

	public String getMessaggio()
	{
		return messaggio;
	}

	public void setMessaggio(String messaggio)
	{
		this.messaggio = messaggio;
	}

}
