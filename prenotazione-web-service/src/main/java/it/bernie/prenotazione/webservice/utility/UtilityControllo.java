package it.bernie.prenotazione.webservice.utility;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.services.DettagliPrenotazioneService;
import it.bernie.prenotazione.webservice.services.DisponibilitaCampoService;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import it.bernie.prenotazione.webservice.services.TesseramentoService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class UtilityControllo {

	@Lazy
	@Autowired
	TesseramentoService servTessC;

	@Lazy
	@Autowired
	ClienteService servClienteC;

	@Lazy
	@Autowired
	DettagliPrenotazioneService servDettC;

	@Lazy
	@Autowired
	PrenotazioneService servPreC;
	
	@Lazy
	@Autowired
    DisponibilitaCampoService servDisponibilitaCampoC;

	private static final String FORMATDATA = "yyyy-MM-dd";

	private static final String FormatOra = "HH:mm";

	public List<Cliente> clientiNonTesserati(List<Cliente> clienti) {
		List<Cliente> clientiTess = new ArrayList<>();

		clientiTess.addAll(clienti);
		List<Tesseramento> tesseramenti = servTessC.selTutti();

		for (int i = 0; i < tesseramenti.size(); i++) {

			for (int j = 0; j < clientiTess.size(); j++) {

				if (clientiTess.get(j).getId() == tesseramenti.get(i).getClienteTess().getId()) {

					clientiTess.remove(j);

				}

			}

		}

		return clientiTess;

	}

	@SneakyThrows
	public void controlloInsDettagli(Prenotazione prenotazione) {

		log.info("Controllo per inserimento dettagli prenotazione");

		if (prenotazione.getCodicePrenotazione() == 0) {

			Integer codPre = (int) Math.random();

			prenotazione.setCodicePrenotazione(codPre);
		}

		Prenotazione pre = servPreC.selByCodicePrenot(prenotazione.getCodicePrenotazione());

		if (pre == null) {
			String ErrMsg = String.format(
					"Tramite seguente codice prenotazione: %s non abbiamo ottenuto nessuna prenotazione",
					prenotazione.getCodicePrenotazione());

			log.warning(ErrMsg);

			throw new NotFoundException(ErrMsg);
		}

	}

	@SneakyThrows
	public List<Tesseramento>     controlloGiocatoriPrenotazione(Prenotazione prenotazione) {
		log.info("Controllo giocatori prenotazione");

		List<Tesseramento> giocatori = new ArrayList<>();

		if (prenotazione.getGiocatore1() != null) {

			giocatori.add(prenotazione.getGiocatore1());

		}

		if (prenotazione.getGiocatore2()!= null) {

			giocatori.add(prenotazione.getGiocatore2());

		}

		if (prenotazione.getGiocatore3() != null) {

			giocatori.add(prenotazione.getGiocatore3());

		}

		if (prenotazione.getGiocatore4() != null) {

			giocatori.add(prenotazione.getGiocatore4());
		}

		int y = 0;

		for (int i = 0; i < giocatori.size(); i++) {

			for (y = i + 1; y < giocatori.size(); y++) {

				if (giocatori.get(i).equals(giocatori.get(y))) {

					String ErrMsg = String.format("Giocatore duplicato nella lista dei giocatori", giocatori.get(i));

					log.warning(ErrMsg);

					throw new DuplicateException(ErrMsg);
				}

			}

		}

		return giocatori;
	}

	@SuppressWarnings("unlikely-arg-type")
	@SneakyThrows
	public void controlloDatePrenotazione(Prenotazione prenotazione) {

		DateFormat df = new SimpleDateFormat(UtilityControllo.FORMATDATA);
//		Date data = df.parse(prenotazione.getData().toString());
//		
//		log.info(data + "*****************data format");

		List<Prenotazione> listPre = servPreC.selByData(prenotazione.getData());
		DateFormat df2 = new SimpleDateFormat(UtilityControllo.FormatOra);
		DateFormat df3 = new SimpleDateFormat(UtilityControllo.FormatOra);

		if (prenotazione.getOraInizio().getTime() >= prenotazione.getOraFine().getTime()) {

			String errMsg = String.format(
					"Impossibile prenotare perche l'orario delle:%s è inferiore o uguale dell'orario di fine partita",
					df2.format(prenotazione.getOraInizio()));

			log.warning(errMsg);

			throw new DuplicateException(errMsg);
		}

		for (int i = 0; i < listPre.size(); i++) {

			String oraInizio = df2.format(listPre.get(i).getOraInizio());
			if (oraInizio.equals(df3.format(prenotazione.getOraInizio())) &&
					listPre.get(i).getCampo().getNumero() == prenotazione.getCampo().getNumero()) {

				String errMsg = String.format(
						"Impossibile prenotare perche l'orario delle:%s scelto ha gia una prenotazione",
						df2.format(prenotazione.getOraInizio()));

				log.warning(errMsg);

				throw new DuplicateException(errMsg);

			}

		}

	}
	
	public static class Disponibility {
		
		@JsonProperty("oraInizio")
		String oraInizio;
		
		@JsonProperty("oraFine")
		String oraFine;
		
		@JsonProperty("available")
		boolean available;
	}
	
	public DisponibilitaCampo updateDisponibilita (Prenotazione prenotazione) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat tf = new SimpleDateFormat("HH:mm");
		
//		String data = df.format(prenotazione.getData());
		String inizio = tf.format(prenotazione.getOraInizio());
		String fine = tf.format(prenotazione.getOraFine());
		
		List<DisponibilitaCampo> disponibilita = servDisponibilitaCampoC.selByCampoDate(
				prenotazione.getCampo().getNumero(), 
				prenotazione.getData());
		
		
		String json = disponibilita.get(0).disponibilita;
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<Disponibility> disp = new ArrayList<Disponibility>();
		
		try {
			disp = mapper.readValue(json , new TypeReference<List<Disponibility>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
		for(int i  =0; i < disp.size(); i++) {
			
			if(disp.get(i).oraInizio.equals(inizio) && disp.get(i).oraFine.equals(fine)) {
				disp.get(i).available = false;
			}
			
		}
		 
		String jsonNew = "";
		
		try {
			jsonNew = mapper.writeValueAsString(disp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		DisponibilitaCampo addNewDisponibility = new DisponibilitaCampo(
				disponibilita.get(0).getId(),
				disponibilita.get(0).getCampo(),
				disponibilita.get(0).getGiorno(),
				jsonNew);
		
		return addNewDisponibility;
		
	}
}
