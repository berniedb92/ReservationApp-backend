package it.bernie.prenotazione.webservice.services;

import java.util.Date;
import java.util.List;

import it.bernie.prenotazione.webservice.entity.Prenotazione;

public interface PrenotazioneService {
	
	public void insPrenotazione(Prenotazione prenotazione);
	
	public List<Prenotazione> selTutte();
	
	public List<Prenotazione> selByData(String data);
	
	public Prenotazione selById(Integer id);

}
