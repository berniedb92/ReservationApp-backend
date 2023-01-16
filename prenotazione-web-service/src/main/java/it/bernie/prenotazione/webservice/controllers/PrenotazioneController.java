package it.bernie.prenotazione.webservice.controllers;

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

		log.info("inseriamo la prenotazione");

		log.info("*************PRENOTAZIONE*************** " + prenotazione);

		Prenotazione checkPrenotazione = servPren.selById(prenotazione.getId());

		if (checkPrenotazione != null) {

			throw new DuplicateException("Errore prenotazione gia nel sistema");
		}

		if(prenotazione.getGiocatore3().getCodiceTessera() == -1
				&& prenotazione.getGiocatore4().getCodiceTessera() == -1) {
			prenotazione.setGiocatore3(null);
			prenotazione.setGiocatore4(null);
		}

		List<Tesseramento> prenotati = controllo.controlloGiocatoriPrenotazione(prenotazione);
      
		 controllo.controlloDatePrenotazione(prenotazione);

		log.info("inseriamo la prenotazione"+ prenotazione.getId());

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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

		LocalDate dataaaa = dateTime.toLocalDate();

		log.info(String.format("otteniamo le prenotazioni nella giornata %s", dataaaa.toString()));

		List<Prenotazione> prenotazioni = servPren.selByData(dataaaa.toString());

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

		if(date.length() > 10) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String[] split = date.split("\\.");
			log.info(split[0]);
			LocalDateTime dateTime = LocalDateTime.parse(split[0], formatter);

			LocalDate dataaaa = dateTime.toLocalDate();
			log.info(String.format("otteniamo le prenotazioni nella giornata %s del campo %d", dataaaa.toString(), campo));

			dataString = dataaaa.toString();

		} else {

			log.info(String.format("otteniamo le prenotazioni nella giornata %s del campo %d", date, campo));

			dataString = date;
		}

		List<Prenotazione> prenotazioni = servPren.selByDataAndCampo(dataString, campo);

		if(prenotazioni.isEmpty()) {

			String errMsg = String.format("Nessuna prenotazione trovata per la data %s nel campo %d", date, campo);

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		}

		return new ResponseEntity<List<Prenotazione>>(prenotazioni, HttpStatus.OK);

	}
	
}
