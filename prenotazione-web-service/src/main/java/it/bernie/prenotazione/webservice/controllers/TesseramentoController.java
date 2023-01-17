package it.bernie.prenotazione.webservice.controllers;

import java.net.http.HttpHeaders;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.IntegrazioneTessera;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.entity.TipoTessera;
import it.bernie.prenotazione.webservice.exceptions.CheckingException;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.InfoMsg;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.repository.IntegrazioneTesseraRepository;
import it.bernie.prenotazione.webservice.repository.TipoTesseraRepository;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import it.bernie.prenotazione.webservice.services.TesseramentoService;
import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;
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
	
	@Autowired
	UtilityCalcolo calcolo;
	
	@GetMapping(path = "/list-tesseramenti")
    @SneakyThrows
    private ResponseEntity<List<Tesseramento>> actionLoadTessere() {

		log.info("Carichiamo tutti i tesserati");
        
        List<Tesseramento> tesseramenti = servTess.selTutti();
        
        if(tesseramenti.isEmpty()) {

			String errMsg = "Nessun cliente tesserato trovato!!";

			log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<Tesseramento>>(tesseramenti, HttpStatus.OK);
        
    }
	
	@GetMapping(path = "/list-tesseramenti-id/{codice}")
    @SneakyThrows
    private ResponseEntity<Tesseramento> actionLoadTessera(@PathVariable(value = "codice") Integer codice) {

		log.info(String.format("Carichiamo il tesserato con codice %d", codice));
        
        Tesseramento tesseramento = servTess.selByCodiceTessera(codice);
        
        if(tesseramento == null) {

			String errMsg = String.format("Nessun cliente tesserato trovato con id %d!!", codice);

			log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<Tesseramento>(tesseramento, HttpStatus.OK);
        
    }

	@GetMapping(path = "/list-tesseramenti-cognome/{cognome}")
	@SneakyThrows
	private ResponseEntity<List<Tesseramento>> actionLoadTessera(@PathVariable(value = "cognome") String cognome) {

		log.info(String.format("Carichiamo il tesserato con cognome %s", cognome));

		List<Tesseramento> tesseramento = servTess.selByCognome(cognome);

		if(tesseramento == null) {

			String errMsg = String.format("Nessun cliente tesserato trovato con cognome %s!!", cognome);

			log.warning(errMsg);

			throw new NotFoundException(errMsg);

		}

		return new ResponseEntity<List<Tesseramento>>(tesseramento, HttpStatus.OK);

	}
	
	@GetMapping(path = "/integrazione")
    @SneakyThrows
    private ResponseEntity<List<IntegrazioneTessera>> actionIntegrazioneTessera() {

		log.info("Carichiamo integrazione tessere");
        
        List<IntegrazioneTessera> integrazione = repoInTess.findAll();
        
        if(integrazione.isEmpty()) {

			String errMsg = "errore caricamento integrazione tessera!!";

			log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<IntegrazioneTessera>>(integrazione, HttpStatus.OK);
        
    }
	
	@GetMapping(path = "/tipo")
    @SneakyThrows
    private ResponseEntity<List<TipoTessera>> actionTipoTessera() {

		log.info("Carichiamo tipo tessere");
        
        List<TipoTessera> tipo = repoTipoTess.findAll();
        
        if(tipo.isEmpty()) {

			String errMsg = "errore caricamento tipo tessera!!";

			log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        return new ResponseEntity<List<TipoTessera>>(tipo, HttpStatus.OK);
        
    }
	
	@PostMapping(value = "/new-tessera")
    @SneakyThrows
    private ResponseEntity<InfoMsg> actionAddTessera(@RequestBody Tesseramento tesseramento) {
		
		log.info(String.format("otteniamo codice %s", tesseramento.getCodiceTessera()));
    	
    	Tesseramento tesseraCheck = servTess.selByClienteId(tesseramento.getClienteTess().getId());
    	
    	if(tesseraCheck != null) {

			String errMsg = String.format
					("Cliente %s già tesserato!", tesseramento.getClienteTess().getCodiceFiscale());

			log.warning(errMsg);
    		
    		throw new DuplicateException(errMsg);
    		
    	}
		log.info("Inseriamo il tesserato con codice " + tesseramento.getCodiceTessera());

    	servTess.insTessera(tesseramento);
    	
    	LocalDate dataAtt = LocalDate.now();
    	
    	if(tesseramento.getScadenzaCertificato() == null || !tesseramento.isAttiva() || tesseramento.getScadenzaCertificato().isBefore(dataAtt)) {

			String errMsg = "impossibile attivare la tessera campi richiesti non validi!!";

			log.warning(errMsg);
    		
    		throw new CheckingException(errMsg);
    		
    	}
        
    	return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
    			String.format("Tessera del cliente %s generata con successo!!", 
    					tesseramento.getClienteTess().getCognome())), HttpStatus.CREATED);
        
    }
	
	@RequestMapping(value = "/modifica-tessera", method = RequestMethod.PUT)
	@SneakyThrows
	public ResponseEntity<InfoMsg> updateArt(@RequestBody Tesseramento tesseramento) {
		
		log.info("Modifichiamo il tesserato con codice " + tesseramento.getCodiceTessera());

		Tesseramento checkTes = servTess.selByCodiceTessera(tesseramento.getCodiceTessera());
		
		if(checkTes == null) {
			
			String MsgErr = String.format("Tesserato %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", tesseramento.getCodiceTessera());
			
			log.warning(MsgErr);
			
			throw new NotFoundException(MsgErr);
			
		}
		
		servTess.insTessera(tesseramento);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
				String.format("Tessera del cliente %s modificata con successo!!", 
    					tesseramento.getClienteTess().getCognome())), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(path = "/remove-tessera/{codice}", method = RequestMethod.DELETE)
    @SneakyThrows
    public ResponseEntity<?> actionRemoveTessera(@PathVariable("codice") Integer codice) {

		log.info("Eliminiamo il tesserato con codice " + codice);
        
    	Tesseramento tessera = servTess.selByCodiceTessera(codice);
        
        if(tessera == null) {

			String errMsg = "Nessun tessera trovata con il codice selezionato, impossibile effettuare l'eliminazione!!";

			log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        } 
    	
    	servTess.deleteTessera(tessera);
    	
    	ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", String.format("Eliminazione tessera %d eseguita Con Successo", codice));
		
		return new ResponseEntity<>(responseNode, HttpStatus.OK);
        
    }
	
	@GetMapping(path = "/quote-tessere/{codice}")
    @SneakyThrows
    private ResponseEntity<?> actionLoadQuoteTessera(@PathVariable(value = "codice") Integer codice) {

		log.info(String.format("otteniamo le quote del cliente con codice", codice));
        
        Tesseramento tesseramento = servTess.selByCodiceTessera(codice);
        
        if(tesseramento == null) {

			String errMsg = "Nessun tessera trovata con il codice selezionato, impossibile visualizzatre le quote!!";

			log.warning(errMsg);
        	
        	throw new NotFoundException(errMsg);
        	
        }
        
        Map<String, Float> quota = calcolo.panoramicaQuote(tesseramento);
        
        String[] sport = new String[3];
        Float[] prezzo = new Float[3];
        
        int i = 0;
        for(Map.Entry<String, Float> p : quota.entrySet()){
        	sport[i] = p.getKey();
        	i++;
        } 
        
        i=0;
        for(Map.Entry<String, Float> p : quota.entrySet()){
            prezzo[i] = p.getValue();
            i++;
        }
        
        ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put(sport[0], prezzo[0]);
		responseNode.put(sport[1], prezzo[1]);
		responseNode.put(sport[2], prezzo[2]);
        
        return new ResponseEntity<>(responseNode, HttpStatus.OK);
        
    }

}
