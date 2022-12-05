package it.bernie.prenotazione.webservice.controllers;

import java.time.LocalDate;
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

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.IntegrazioneTessera;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.entity.TipoTessera;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.repository.IntegrazioneTesseraRepository;
import it.bernie.prenotazione.webservice.repository.TipoTesseraRepository;
import it.bernie.prenotazione.webservice.services.TesseramentoService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/api/reservation")
@Log
public class TesseramentoController {
	
	@Autowired
	TesseramentoService servTess;
	
	@Autowired
	IntegrazioneTesseraRepository repoInTess;
	
	@Autowired
	TipoTesseraRepository repoTipoTess;
	
	@GetMapping(path = "/list-tesseramenti")
    @SneakyThrows
    private ResponseEntity<List<Tesseramento>> actionLoadClienti() {
        
        List<Tesseramento> tesseramenti = servTess.selTutti();
        
        if(tesseramenti.isEmpty()) {
        	
        	throw new NotFoundException("Nessun cliente tesserato trovato!!");
        	
        }
        
        return new ResponseEntity<List<Tesseramento>>(tesseramenti, HttpStatus.OK);
        
    }
	
	@GetMapping(path = "/list-tesseramenti-id/{codice}")
    @SneakyThrows
    private ResponseEntity<Tesseramento> actionLoadClienti(@PathVariable(value = "codice") Integer codice) {
        
        Tesseramento tesseramento = servTess.selByCodiceTessera(codice);
        
        if(tesseramento == null) {
        	
        	throw new NotFoundException("Nessun cliente tesserato trovato!!");
        	
        }
        
        return new ResponseEntity<Tesseramento>(tesseramento, HttpStatus.OK);
        
    }
	
	@GetMapping(path = "/integrazione")
    @SneakyThrows
    private ResponseEntity<List<IntegrazioneTessera>> actionIntegrazioneTessera() {
        
        List<IntegrazioneTessera> integrazione = repoInTess.findAll();
        
        if(integrazione.isEmpty()) {
        	
        	throw new NotFoundException("errore caricamento integrazione tessera!!");
        	
        }
        
        return new ResponseEntity<List<IntegrazioneTessera>>(integrazione, HttpStatus.OK);
        
    }
	
	@GetMapping(path = "/tipo")
    @SneakyThrows
    private ResponseEntity<List<TipoTessera>> actionTipoTessera() {
        
        List<TipoTessera> tipo = repoTipoTess.findAll();
        
        if(tipo.isEmpty()) {
        	
        	throw new NotFoundException("errore caricamento integrazione tessera!!");
        	
        }
        
        return new ResponseEntity<List<TipoTessera>>(tipo, HttpStatus.OK);
        
    }
	
	@PostMapping(value = "/new-tessera")
    @SneakyThrows
    private ResponseEntity<InfoMsg> actionAddCliente(@RequestBody Tesseramento tesseramento) {
		
		log.info(String.format("porcodiii %s", tesseramento.getCodiceTessera()));
    	
    	Tesseramento tesseraCheck = servTess.selByClienteId(tesseramento.getClienteTess().getId());
    	
    	if(tesseraCheck != null) {
    		
    		throw new DuplicateException(String.format
    				("Cliente %s già tesserato!", tesseramento.getClienteTess().getCodiceFiscale()));
    		
    	}
    	
    	servTess.insTessera(tesseramento);
        
    	return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
    			"Cliente inserito con successo!!"), HttpStatus.CREATED);
        
    }

}
