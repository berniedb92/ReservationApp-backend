package it.bernie.prenotazione.webservice.services;

import java.util.List;

import it.bernie.prenotazione.webservice.entity.Tesseramento;

public interface TesseramentoService {
	
	public Tesseramento selByCodiceTessera(Integer codiceTessera);
	
	public Tesseramento selByClienteId(Integer id);

	public List<Tesseramento> selByCognome(String cognome);

	public List<Tesseramento> selTutti();
	
	public void insTessera(Tesseramento t);
	
	public void deleteTessera(Tesseramento t);

}
