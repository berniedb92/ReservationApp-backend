package it.bernie.prenotazione.webservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.bernie.prenotazione.webservice.entity.Campo;
import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.services.CampoService;
import it.bernie.prenotazione.webservice.services.ClienteService;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/api/reservation")
public class CampoController {
    
    @Autowired
    CampoService servCampo;
    
    @GetMapping(path = "/list-campi")
    @SneakyThrows
    private ResponseEntity<List<Campo>> actionLoadClienti() {
        
        List<Campo> clienti =  servCampo.selTutti();
        
        if(clienti.isEmpty()) {
        	
        	throw new NotFoundException("Nessun campo trovato!!");
        	
        }
        
        return new ResponseEntity<List<Campo>>(clienti, HttpStatus.OK);
        
    }
    
    @GetMapping(path = "/campo/{id}")
    @SneakyThrows
    private ResponseEntity<Campo> actionLoadCampoById(@PathVariable Integer id) {
        
        Campo campo =  servCampo.selCampoById(id);
        
        if(campo == null) {
        	
        	throw new NotFoundException("Nessun campo trovato!!");
        	
        }
        
        return new ResponseEntity<Campo>(campo, HttpStatus.OK);
        
    }
}