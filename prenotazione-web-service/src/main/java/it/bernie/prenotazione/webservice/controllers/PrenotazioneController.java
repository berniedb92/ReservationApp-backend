package it.bernie.prenotazione.webservice.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import it.bernie.prenotazione.webservice.services.DisponibilitaCampoService;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import it.bernie.prenotazione.webservice.services.TesseramentoService;
import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;
import it.bernie.prenotazione.webservice.utility.UtilityControllo;
import lombok.SneakyThrows;
@Log
@RestController
@RequestMapping("/api/reservation/prenotazione")
public class PrenotazioneController {

	@Autowired
	PrenotazioneService servPren;
	
	@Autowired
	UtilityControllo controllo;
	
	@Autowired
	UtilityCalcolo calcolo;

	@PostMapping(value = "/inserisci")
	@SneakyThrows
	public ResponseEntity<InfoMsg> actionInsertPrenotazione(@RequestBody Prenotazione prenotazione) {

		log.info("inseriamo la prenotazione");

		log.info("*************PRENOTAZIONE*************** " + prenotazione);

		Prenotazione checkPrenotazione = servPren.selById(prenotazione.getId());

		if (checkPrenotazione != null) {
			
			String errMsg = "\"Errore prenotazione gia nel sistema\"";

			log.warning(errMsg);

			throw new DuplicateException(errMsg);
		}

		if(prenotazione.getGiocatore1() == null
				|| prenotazione.getGiocatore2() == null) {
			
			String errMsg = "Errore!! Giocatori non presenti nella prenotazione!!";

			log.warning(errMsg);

			throw new NotFoundException(errMsg);
		}
      
		controllo.controlloDatePrenotazione(prenotazione);

		log.info("inseriamo la prenotazione"+ prenotazione.getId());

		servPren.insPrenotazione(prenotazione);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Prenotazione inserita con successo"),
				HttpStatus.CREATED);

	}

	@SneakyThrows
	@GetMapping(value = "/list-pren")
	public ResponseEntity<List<Prenotazione>> actionListPrenotazione() {

		log.info("otteniamo tutte le prenotazioni");

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

		log.info(String.format("otteniamo le prenotazioni nella giornata %s", date));
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateD = format.parse(date);

		List<Prenotazione> prenotazioni = servPren.selByData(dateD);

		if(prenotazioni.isEmpty()) {

			String errMsg = String.format("Nessuna prenotazione trovata per la data %s", date);

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		}

		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);

	}

	@SneakyThrows
	@GetMapping(value = "/list-pren-campo/{date}/{campo}")
	public ResponseEntity<List<Prenotazione>> actionListPrenotazioneDate(@PathVariable String date, @PathVariable Integer campo) {

		String dataString = "";

		log.info(String.format("******************la data che arriva è***************** %s del campo %d", date, campo));
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateD = format.parse(date);

		List<Prenotazione> prenotazioni = servPren.selByDataAndCampo(dateD, campo);

		if(prenotazioni.isEmpty()) {

			String errMsg = String.format("Nessuna prenotazione trovata per la data %s nel campo %d", date, campo);

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		}

		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);

	}
	
}
