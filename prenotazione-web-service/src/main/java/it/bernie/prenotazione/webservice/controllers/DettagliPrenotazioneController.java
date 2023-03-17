package it.bernie.prenotazione.webservice.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;
import it.bernie.prenotazione.webservice.exceptions.BindingException;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.services.CampoService;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.services.DettagliPrenotazioneService;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/api/reservation")
@Log
public class DettagliPrenotazioneController {

	@Autowired
	PrenotazioneService servPren;

	@Autowired
	CampoService servCampo;

	@Autowired
	ClienteService servCliente;

	@Autowired
	DettagliPrenotazioneService dettagliPrenotazioneService;

	@Autowired
	private ResourceBundleMessageSource errMessage;

	// Con il seguente metodo ricerchiamo i dettagli-prenotazione della prenotazione
	// tramite codice Prenotazione cosi otterremo tutti i giocatori della partita.

	@SneakyThrows
	@GetMapping(value = "/dett-pre/cerca/codPren/{codPre}")
	public ResponseEntity<List<DettagliPrenotazione>> selDettagliPrenoByCodPre(@PathVariable("codPre") Integer codPre) {

		log.info("Otteniamo i dettagli della prenotazione di tutti i giocatori");

		List<DettagliPrenotazione> dettPrenotazione = dettagliPrenotazioneService
				.selectPrenotazioneByCodPrenotazione(codPre);

		if (dettPrenotazione.isEmpty()) {

			String ErrMsg = String.format(
					"Tramite il seguente codice-prenotazione : %s non abbiamo ottenuto nessun risultato", codPre);

			log.warning(ErrMsg);

			throw new NotFoundException(ErrMsg);
		}

		return new ResponseEntity<List<DettagliPrenotazione>>(dettPrenotazione, new HttpHeaders(), HttpStatus.OK);

	}

	@SneakyThrows
	@GetMapping(value = "/dett-pre/cerca/dettPreno/{codPre}/{idTessera}", produces = "application/json")
	public ResponseEntity<DettagliPrenotazione> selDettPrenByCodPreAndCodTess(@PathVariable("codPre") Integer codPre,
			@PathVariable("idTessera") Integer idTessera) {

		log.info("Otteniamo i dettagli di un Giocatore su una specifica prenotazione " + codPre);

		DettagliPrenotazione dettPrenotazione = dettagliPrenotazioneService
				.selectPrenotazioneByCodPrenotazioneAndIdCliente(codPre, idTessera);

		if (dettPrenotazione == null) {

			String ErrMsg = String.format(
					"Tramite il seguente codice-prenotazione: %s  e codice-tessera: %s non abbiamo ottenuto nessun risultato",
					codPre, idTessera);

			log.warning(ErrMsg);

			throw new NotFoundException(ErrMsg);

		}

		return new ResponseEntity<DettagliPrenotazione>(dettPrenotazione, new HttpHeaders(), HttpStatus.OK);
	}

	@SneakyThrows
	@PostMapping(value = "/dett-pre/inserisci")
	public ResponseEntity<InfoMsg> inserisciDettaglioPrenotazione(
			@Valid @RequestBody DettagliPrenotazione dettagliPrenotazione, BindingResult bindingResult) {

		log.info("**** Inserimento dettagli prenotazione ****");

		if (bindingResult.hasErrors()) {

			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

			log.warning(errMsg);

			throw new BindingException(errMsg);
		}

		List<DettagliPrenotazione> dett = dettagliPrenotazioneService.selectPrenotazioneByCodPrenotazione(
				dettagliPrenotazione.getCodicePrenotazione().getCodicePrenotazione());

		if (!dett.isEmpty()) {

			String errMsg = String.format("Codice Prenotazione %s gia presente nella lista dettagli",
					dettagliPrenotazione.getCodicePrenotazione().getCodicePrenotazione());

			log.warning(errMsg);

			throw new DuplicateException(errMsg);

		} else {

			return new ResponseEntity<InfoMsg>(
					new InfoMsg(LocalDate.now(), "Inserimento dettaglio avvenuto con successo"), HttpStatus.CREATED);
		}

	}

	@SneakyThrows
	@PostMapping(value = "/dett-pre/modifica")
	public ResponseEntity<InfoMsg> modificaDettaglioPrenotazione(
			@Valid @RequestBody DettagliPrenotazione dettagliPrenotazione, BindingResult bindingResult) {

		log.info("**** Inserimento dettagli prenotazione ****");

		if (bindingResult.hasErrors()) {

			String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

			log.warning(errMsg);

			throw new BindingException(errMsg);
		}

		DettagliPrenotazione dett = dettagliPrenotazioneService.selectPrenotazioneByCodPrenotazioneAndIdCliente(
				dettagliPrenotazione.getCodicePrenotazione().getCodicePrenotazione(),
				dettagliPrenotazione.getCliente().getCodiceTessera());

		if (dett == null) {

			String errMsg = String.format("Codice Prenotazione %s non presente nella lista dettagli",
					dettagliPrenotazione.getCodicePrenotazione().getCodicePrenotazione());

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		} else {

			dettagliPrenotazioneService.insertDettaglioPrenotazione(dettagliPrenotazione);

			return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Modifica dettaglio avvenuto con successo"),
					HttpStatus.CREATED);
		}

	}

	@SneakyThrows
	@DeleteMapping(value = "/dett-pre/elimina/{idDett}")
	public ResponseEntity<?> deleteDettPrenotazione(@PathVariable("idDett") Integer idDett) {

		log.info("ELiminazione dettaglio Prenotazione ");

		DettagliPrenotazione dettagliPrenotazione = dettagliPrenotazioneService.selectDettByIdDett(idDett);

		if (dettagliPrenotazione == null) {

			String errMsg = String.format("Elemento %s non presente nella lista dettagliPrenotazione", idDett);
		}

		dettagliPrenotazioneService.deleteDettaglioPrenotazione(dettagliPrenotazione);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione " + "Dettaglio " + idDett + " Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
	}

}
