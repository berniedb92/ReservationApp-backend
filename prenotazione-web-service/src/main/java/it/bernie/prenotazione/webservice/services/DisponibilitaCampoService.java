package it.bernie.prenotazione.webservice.services;

import java.time.LocalDate;
import java.util.List;

import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;


public interface DisponibilitaCampoService {

	public List<DisponibilitaCampo> selTutti();
	
	public List<DisponibilitaCampo> selByCampo(Integer campo, String giorno);
	
}
