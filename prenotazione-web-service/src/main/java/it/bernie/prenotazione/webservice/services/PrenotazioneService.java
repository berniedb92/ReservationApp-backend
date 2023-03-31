package it.bernie.prenotazione.webservice.services;

import java.util.Date;
import java.util.List;

import it.bernie.prenotazione.webservice.entity.Prenotazione;

public interface PrenotazioneService {
	
	public void insPrenotazione(Prenotazione prenotazione);
	
	public List<Prenotazione> selTutte();
	
	public List<Prenotazione> selByData(Date data);

	public List<Prenotazione> selByDataAndCampo(Date data, Integer campo);
	
	public Prenotazione selById(Integer id);
	
	public Prenotazione selByCodicePrenot(Integer codPre);
	
	public List<Prenotazione> selByDataPrenotazione(String data);
}
