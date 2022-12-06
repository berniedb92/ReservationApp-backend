package it.bernie.prenotazione.webservice.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Sport;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.repository.SportRepository;
import it.bernie.prenotazione.webservice.services.CampoService;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/api/reservation")
public class PrenotazioneController {
	
	@Autowired	
	PrenotazioneService servPren;
		
	@Autowired
	CampoService servCampo;
	    
	@Autowired
	ClienteService servCliente;
	
	@Autowired
	SportRepository repoSport;
	
	@PostMapping(value = "/inserisci")
	@SneakyThrows
	public ResponseEntity<InfoMsg> actionInsertPrenotazione(@RequestBody Prenotazione prenotazione) {
		
		Prenotazione checkPrenotazione = servPren.selById(prenotazione.getId());
		
		if(checkPrenotazione != null) {
			
			throw new DuplicateException("Errore prenotazione gia nel sistema");
		}
		
		servPren.insPrenotazione(prenotazione);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Prenotazione inserita con successo"), HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/list-pren")
	public ResponseEntity<List<Prenotazione>> actionListPrenotazione() {
		
		List<Prenotazione> prenotazioni = servPren.selTutte();
		
		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/list-pren-date/{date}")
	public ResponseEntity<List<Prenotazione>> actionListPrenotazioneDate(@PathVariable String date) {
		
		List<Prenotazione> prenotazioni = servPren.selByData(date);
		
		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/sport")
	public ResponseEntity<List<Sport>> actionSport() {
		
		List<Sport> sport = repoSport.findAll();
		
		return new ResponseEntity<List<Sport>>(sport, HttpStatus.OK);
		
	}

}
