package it.bernie.prenotazione.webservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;
import it.bernie.prenotazione.webservice.repository.DettagliPrenotazioneRepository;

@Service
@Transactional
public class DettagliPrenotazioneServiceImpl implements DettagliPrenotazioneService {
   
	@Autowired
	DettagliPrenotazioneRepository dettagliPrenotazioneRepository;
	
	@Override
	public List<DettagliPrenotazione> selectPrenotazioneByCodPrenotazione(Integer idPrenotazione) {
		
		return  dettagliPrenotazioneRepository.selByCodicePrenotazione(idPrenotazione);
	}

	@Override
	public DettagliPrenotazione selectPrenotazioneByCodPrenotazioneAndIdCliente(Integer idPrenotazione, Integer idTessera) {
		
		return dettagliPrenotazioneRepository.selByCodicePrenotazioneAndCliente(idPrenotazione, idTessera);
	}

	@Override
	public void insertDettaglioPrenotazione(DettagliPrenotazione dettagliPrenotazione) {
		
        dettagliPrenotazioneRepository.save(dettagliPrenotazione);
		
	}

	@Override
	public void deleteDettaglioPrenotazione(DettagliPrenotazione dettagliPrenotazione) {
	 
		dettagliPrenotazioneRepository.delete(dettagliPrenotazione);
		
	}

	@Override
	public DettagliPrenotazione selectDettByIdDett(Integer idDett) {
		
		return dettagliPrenotazioneRepository.getById(idDett);
	}

}
