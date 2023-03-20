package it.bernie.prenotazione.webservice.controllers;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bernie.prenotazione.webservice.entity.Campo;
import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.services.CampoService;
import it.bernie.prenotazione.webservice.services.DisponibilitaCampoService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/api/reservation")
@Log
public class DisponibilitaCampoController {
	
    @Autowired
    DisponibilitaCampoService servDisponibilitaCampo;
    
    @GetMapping(path = "/availability")
    @SneakyThrows
    private ResponseEntity<List<DisponibilitaCampo>> actionLoadAvailability() {
    	
    	log.info("Carichiamo la disponibilita dei campi");
        
        List<DisponibilitaCampo> disponibilita =  servDisponibilitaCampo.selTutti();
        
        if(disponibilita.isEmpty()) {
        	
        	String errMsg = "Non ci sono dati!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<DisponibilitaCampo>>(disponibilita, HttpStatus.OK);
    }
    
    @GetMapping(path = "/availability/{campo}/{giorno}")
    @SneakyThrows
    private ResponseEntity<List<DisponibilitaCampo>> 
    actionLoadAvailabilityCampo(@PathVariable Integer campo, @PathVariable String giorno) {
    	
    	log.info("Carichiamo la disponibilita del campo " + campo);
        
        List<DisponibilitaCampo> disponibilita =  servDisponibilitaCampo.selByCampo(campo, giorno);
        
        if(disponibilita.isEmpty()) {
        	
        	String errMsg = "Non ci sono dati!!";
        	
        	log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<DisponibilitaCampo>>(disponibilita, HttpStatus.OK);
    }

}
