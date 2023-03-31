package it.bernie.prenotazione.webservice.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.ObjectWriter;
import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;
import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.repository.DettagliPrenotazioneRepository;
import it.bernie.prenotazione.webservice.repository.DisponibilitaCampoRepository;
import it.bernie.prenotazione.webservice.repository.PrenotazioneRepository;
import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;
import it.bernie.prenotazione.webservice.utility.UtilityControllo;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrenotazioneServiceImpl implements PrenotazioneService {
	
	@Autowired
	PrenotazioneRepository repoPren;
	
	@Autowired
	DisponibilitaCampoRepository dispCampoRepo;
	
	@Autowired
	DettagliPrenotazioneRepository dettagliRepositoryService;
	
	@Autowired
	UtilityControllo controlloDisp;
	
	@Autowired
	UtilityCalcolo calcoloDisp;
	
	@Override
	public void insPrenotazione(Prenotazione prenotazione) {
		
		if(prenotazione.getModalita() == null) {
			prenotazione.setModalita("padel");
		} 
		
		List<Tesseramento> prenotati = controlloDisp.
				controlloGiocatoriPrenotazione(prenotazione);
		
		repoPren.save(prenotazione);
		dispCampoRepo.save(controlloDisp.updateDisponibilita(prenotazione));
		
		for (int i = 0; i < prenotati.size(); i++) {

			DettagliPrenotazione dettPre = new DettagliPrenotazione(
					prenotazione, prenotati.get(i), 
					calcoloDisp.gestioneQuote(prenotati.get(i),prenotazione), 
					false);

			dettagliRepositoryService.save(dettPre);
		}
		
	}

	@Override
	public List<Prenotazione> selTutte() {
		return repoPren.findAll();
	}

	@Override
	public List<Prenotazione> selByData(Date data) {
		return repoPren.selByData(data);
	}

	@Override
	public List<Prenotazione> selByDataAndCampo(Date data, Integer campo) {
		return repoPren.selPrenotazioniByDataAndCampo(data, campo);
	}

	@Override
	public Prenotazione selById(Integer id) {
		
		Optional<Prenotazione> pren = repoPren.findById(id);
		Prenotazione p = null;
		
		if(!pren.isEmpty()) {
			p = pren.get();
		}
		
		return p;
	}

	@Override
	public Prenotazione selByCodicePrenot(Integer codPre) {
		
		return repoPren.selByCodicePrenotazione(codPre);
	}

	@Override
	public List<Prenotazione> selByDataPrenotazione(String data) {
	
		return repoPren.selPrenotazioniByData(data);
	}

}
