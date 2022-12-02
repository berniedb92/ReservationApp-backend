package com.gestuser.webapp.services;

import java.util.List;

import com.gestuser.webapp.models.Utenti;

public interface UtentiService {
	
	public List<Utenti> SelTutti();
	
	public Utenti findByUserId(String UserId);
	
	public void InsUtente(Utenti utente);
	
	public void DelUtente(Utenti utente);

}
