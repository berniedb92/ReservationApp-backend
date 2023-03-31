package com.gestuser.webapp.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gestuser.webapp.exception.BindingException;
import com.gestuser.webapp.exception.NotFoundException;
import com.gestuser.webapp.models.Utenti;
import com.gestuser.webapp.services.UtentiService;



@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "http://localhost:4200")
public class UtentiController {
	
	private static final Logger log = LoggerFactory.getLogger(UtentiController.class);
	
	@Autowired
	UtentiService utentiService;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping(value = "/cerca/tutti")
	public ResponseEntity<List<Utenti>> SelUtenti() throws NotFoundException {
		
		log.info("selezioniamo tutti gli utenti");
	
		List<Utenti> utenti = utentiService.SelTutti();
		
		if(utenti.isEmpty()) {
			String msgErr = ("nessun utente trovato!!");
			
			log.warn(msgErr);
			
			throw new NotFoundException(msgErr);
		}
		
		return new ResponseEntity<List<Utenti>>(utenti, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/cerca/userid/{id}")
	public ResponseEntity<Utenti> SelUtenti(@PathVariable("id") String id) throws NotFoundException {
		log.info("Otteniamo l'utente " + id);
		
		Utenti utente = utentiService.findByUserId(id);
		
		if(utente == null) {
			String msgErr = String.format("Utente %s non trovato!!", id);
			
			log.warn(msgErr);
			
			throw new NotFoundException(msgErr);
		}
		
		return new ResponseEntity<Utenti>(utente, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/inserisci")
	public ResponseEntity<InfoMsg> InsUtente(@RequestBody Utenti utente) {
		
		log.info("inizio inserimento"+ utente.getUserid());
		
		Utenti checkUtente = utentiService.findByUserId(utente.getUserid());
		
		log.info("utente" + checkUtente);
		
		if(checkUtente != null) {
			
			utente.setId(checkUtente.getId());
			log.info("Modifica utente");
			
		} else {
			
			log.info("Inserimento utente");
			
		}
		
//		if(bindingResult.hasErrors()) {
//			String msgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
//			
//			log.warn(msgErr);
//			
//			throw new BindingException(msgErr);
//		}
		
		String encodedPassword = passwordEncoder.encode(utente.getPassword());
		utente.setPassword(encodedPassword);
		utentiService.InsUtente(utente);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
				String.format("Inserimento Utente %s Eseguita Con Successo", utente.getUserid())), new HttpHeaders(), HttpStatus.CREATED);
		
	}
	
	@DeleteMapping(value = "/elimina/{id}")
	public ResponseEntity<?> DelUtente(@PathVariable("id") String id) throws NotFoundException {
		log.info("eliminiamo l'utente" + id);
		
		Utenti utente = utentiService.findByUserId(id);
		
		if(utente == null) {
			String msgErr = String.format("Utente %s non presente in anagrafica!", id);
			
			log.warn(msgErr);
			
			throw new NotFoundException(msgErr);
		}
		
		utentiService.DelUtente(utente);
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Utente " + id + " Eseguita Con Successo");
		
		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
		
	}

}
