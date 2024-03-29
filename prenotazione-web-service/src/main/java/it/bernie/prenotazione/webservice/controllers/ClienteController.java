/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bernie.prenotazione.webservice.controllers;


import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.services.CampoService;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;
import it.bernie.prenotazione.webservice.utility.UtilityControllo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

/**
 *
 * @author berni
 */

@Log
@RestController
@RequestMapping("/api/reservation")
public class ClienteController {
    
    @Autowired
    CampoService servCampo;
    
    @Autowired
    ClienteService servCliente;
    
    @Autowired
    UtilityCalcolo calcoloCl;
    
    @Autowired
    UtilityControllo controlloCl;
    
    @GetMapping(path = "/list-clienti")
    @SneakyThrows
    private ResponseEntity<List<Cliente>> actionLoadClienti() {

        log.info("Carichiamo la lista dei clienti");
        
        List<Cliente> clienti =  servCliente.selTutti();
        
        if(clienti.isEmpty()) {
        	
        	String errMsg = "Nessun cliente trovato!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<Cliente>>(clienti, HttpStatus.OK);
        
    }
    
    @GetMapping(path = "/list-clienti-id/{id}")
    @SneakyThrows
    private ResponseEntity<Cliente> actionLoadClientiId(@PathVariable("id") Integer id) {

        log.info("Ricevo l'id " + id);
        
        Optional<Cliente> clienti =  servCliente.selById(id);
        Cliente cliente = null;
        
        if(clienti.isEmpty()) {
        	
        	String errMsg = "Nessun cliente trovato!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        } else {
        	
        	cliente = clienti.get();
        	
        }
        
        if(cliente == null) {
        	
        	String errMsg = String.format("Il cliente con id %d non esiste!", id);
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        
    }
    
    @PostMapping(value = "/add-cliente")
    @SneakyThrows
    private ResponseEntity<InfoMsg> actionAddCliente(@RequestBody Cliente cliente) {

        log.info("**** Inserimento cliente ****");
    	
    	Cliente clienteCheck = servCliente.selByCf(cliente.getCodiceFiscale());
    	
    	if(clienteCheck != null) {
    		
    		String errMsg = String.format
    				("Cliente %s già presente in anagrafica!", cliente.getCodiceFiscale());
    		
    		log.warning(errMsg);
    		
    		throw new DuplicateException(errMsg);
    		
    	}
    	
    	servCliente.insCliente(cliente);
        
    	return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
    			"Cliente inserito con successo!!"), HttpStatus.CREATED);
        
    }
    
    @RequestMapping(path = "/remove-cliente/{id}", method = RequestMethod.DELETE)
    @SneakyThrows
    public ResponseEntity<?> actionRemoveCliente(@PathVariable("id") Integer id) {

        log.info(String.format("eliminiamo il cliente con id %d", id));
        
    	Optional<Cliente> clienti = servCliente.selById(id);
        Cliente cliente = null;
        
        if(clienti.isEmpty()) {

            String errMsg = "Nessun cliente trovato!!";

            log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        } else {
        	
        	cliente = clienti.get();
        	
        }
    	
    	if(cliente == null) {

            String errMsg = String.format("Cliente %d non presente in anagrafica", id);

            log.warning(errMsg);
    		
    		throw new NotFoundException(errMsg);
    		
    	}
    	
    	servCliente.delCliente(cliente);
    	
    	ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", String.format("Eliminazione cliente %d eseguita Con Successo", id));
		
		return new ResponseEntity<>(responseNode, HttpStatus.OK);
        
    }
    
    @RequestMapping(path = "/update-cliente", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<InfoMsg> actionUpdateCliente(@RequestBody Cliente cliente) {

        log.info(String.format("modifichiamo il cliente con id %d", cliente.getId()));

    	Cliente clienteCheck = servCliente.selByCf(cliente.getCodiceFiscale());
    	
    	if(clienteCheck == null) {
    		
    		String errMsg = String.format
    				("Cliente %s non presente in anagrafica! "
    						+ "Impossibile effettuare la modifica!!", cliente.getCodiceFiscale());
    		
    		log.warning(errMsg);
    		
    		throw new DuplicateException(errMsg);
    		
    	}
    	
    	servCliente.insCliente(cliente);
        
    	return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
    			"Cliente modificato con successo!!"), HttpStatus.CREATED);
        
    }
    
    @GetMapping(path = "/list-clienti-complex")
    @SneakyThrows
    private ResponseEntity<List<Cliente>> actionLoadClientiComplex() {

        log.info("carichiamo tutti i compleanni della giornata");
        
    	List<Cliente> clienti = servCliente.selTutti();
    	
    	if(clienti.isEmpty()) {
        	
        	String errMsg = "Nessun cliente trovato!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
    	
        List<Cliente> clientiComplex = calcoloCl.compleanni(clienti);;
        
        if(clientiComplex.isEmpty()) {
        	
        	String errMsg = "Nessun compleanno nella giornata di oggi!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<Cliente>>(clientiComplex, HttpStatus.OK);
        
    }
    
    @GetMapping(path = "/list-clienti-notessera")
    @SneakyThrows
    private ResponseEntity<List<Cliente>> actionLoadClientiNoTessera() {

        log.info("carichiamo tutti i clienti senza tessera");
        
        List<Cliente> clienti =  servCliente.selTutti();
        
        if(clienti.isEmpty()) {
        	
        	String errMsg = "Nessun cliente trovato!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        List<Cliente> clientiComplex = controlloCl.clientiNonTesserati(clienti);
        
        if(clienti.isEmpty()) {
        	
        	String errMsg = "Nessun cliente senza tessera!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<Cliente>>(clientiComplex, HttpStatus.OK);
        
    }
    
}
