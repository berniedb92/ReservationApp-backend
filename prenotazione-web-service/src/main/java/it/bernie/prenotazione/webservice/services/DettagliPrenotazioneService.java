package it.bernie.prenotazione.webservice.services;

import java.util.List;
import java.util.Optional;

import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;

public interface DettagliPrenotazioneService {

 public List<DettagliPrenotazione> selectPrenotazioneByCodPrenotazione(Integer idPrenotazione);
 
 public DettagliPrenotazione selectPrenotazioneByCodPrenotazioneAndIdCliente(Integer idPrenotazione, Integer idTessera);
 
 public void insertDettaglioPrenotazione(DettagliPrenotazione dettagliPrenotazione);
 
 public void deleteDettaglioPrenotazione(DettagliPrenotazione dettagliPrenotazione);
 
public DettagliPrenotazione selectDettByIdDett(Integer idDett);
 
}
