package it.bernie.prenotazione.webservice.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.repository.DettagliPrenotazioneRepository;
import it.bernie.prenotazione.webservice.services.CampoService;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.services.DettagliPrenotazioneService;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import it.bernie.prenotazione.webservice.services.TesseramentoService;
import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;
import it.bernie.prenotazione.webservice.utility.UtilityControllo;
import lombok.SneakyThrows;
@Log
@RestController
@RequestMapping("/api/reservation")
public class PrenotazioneController {

	@Autowired
	PrenotazioneService servPren;

	@Autowired
	DettagliPrenotazioneRepository dettagliRepositoryService;

	@Autowired
	UtilityControllo controllo;
	
	@Autowired
	UtilityCalcolo calcolo;

	@PostMapping(value = "/inserisci")
	@SneakyThrows
	public ResponseEntity<InfoMsg> actionInsertPrenotazione(@RequestBody Prenotazione prenotazione) {

		Prenotazione checkPrenotazione = servPren.selById(prenotazione.getId());

		if (checkPrenotazione != null) {

			throw new DuplicateException("Errore prenotazione gia nel sistema");
		}


		List<Tesseramento> prenotati = controllo.controlloGiocatoriPrenotazione(prenotazione);
      
		 controllo.controlloDatePrenotazione(prenotazione);
		

		servPren.insPrenotazione(prenotazione);

		for (int i = 0; i < prenotati.size(); i++) {

			DettagliPrenotazione dettPre = new DettagliPrenotazione
					(prenotazione, prenotati.get(i), calcolo.gestioneQuote(prenotati.get(i), prenotazione), false);

			dettagliRepositoryService.save(dettPre);
		}

		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Prenotazione inserita con successo"),
				HttpStatus.CREATED);

	}

	@SneakyThrows
	@GetMapping(value = "/list-pren")
	public ResponseEntity<List<Prenotazione>> actionListPrenotazione() {

		List<Prenotazione> prenotazioni = servPren.selTutte();

		if(prenotazioni.isEmpty()) {

			String errMsg = "Nessuna prenotazione trovata";

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		}

		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);

	}

	@SneakyThrows
	@GetMapping(value = "/list-pren-date/{date}")
	public ResponseEntity<List<Prenotazione>> actionListPrenotazioneDate(@PathVariable String date) {

		List<Prenotazione> prenotazioni = servPren.selByData(date);

		if(prenotazioni.isEmpty()) {

			String errMsg = String.format("Nessuna prenotazione trovata per la data %s", date);

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		}

		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);

	}
	
}
