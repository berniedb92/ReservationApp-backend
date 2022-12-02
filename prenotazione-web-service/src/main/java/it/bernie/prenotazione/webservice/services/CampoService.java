package it.bernie.prenotazione.webservice.services;

import java.util.List;

import it.bernie.prenotazione.webservice.entity.Campo;

public interface CampoService {
	
	public List<Campo> selTutti();
	
	public void insCampo(Campo campo);
	
	public void delCampo(Campo campo);

}
