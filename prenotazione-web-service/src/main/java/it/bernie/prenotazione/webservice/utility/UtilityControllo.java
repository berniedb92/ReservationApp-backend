package it.bernie.prenotazione.webservice.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
import it.bernie.prenotazione.webservice.repository.DettagliPrenotazioneRepository;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.services.DettagliPrenotazioneService;
import it.bernie.prenotazione.webservice.services.PrenotazioneService;
import it.bernie.prenotazione.webservice.services.TesseramentoService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Service
@Log
public class UtilityControllo {

	@Autowired
	TesseramentoService servTess;

	@Autowired
	ClienteService servCliente;

	@Autowired
	DettagliPrenotazioneService servDett;


	@Autowired
	PrenotazioneService servPre;

	public List<Cliente> clientiNonTesserati(List<Cliente> clienti) {
		List<Cliente> clientiTess = new ArrayList<>(); 

		clientiTess.addAll(clienti);
		List<Tesseramento> tesseramenti = servTess.selTutti();

		for(int i =0; i< tesseramenti.size(); i++) {

			for(int j =0; j< clientiTess.size(); j++) {

				if(clientiTess.get(j).getId() == tesseramenti.get(i).getClienteTess().getId()) {

					clientiTess.remove(j);

				}

			}

		}

		return clientiTess;

	}
	@SneakyThrows
	public void controlloInsDettagli(Prenotazione prenotazione) {

		log.info("Controllo per inserimento dettagli prenotazione");

		Prenotazione pre = servPre.selByCodicePrenot(prenotazione.getCodicePrenotazione());

		if(pre == null) {
			String ErrMsg  = String.format("Tramite seguente codice prenotazione: %s non abbiamo ottenuto nessuna prenotazione",prenotazione.getCodicePrenotazione());

			log.warning(ErrMsg);

			throw new NotFoundException(ErrMsg);
		}

	}
	
	
	@SneakyThrows
	public List<Integer> controlloGiocatoriPrenotazione(Prenotazione prenotazione) {
		log.info("Controllo giocatori prenotazione");

		List<Integer> giocatori = new ArrayList<>();

		Prenotazione pre = servPre.selByCodicePrenot(prenotazione.getCodicePrenotazione());

		if(pre == null) {
			String ErrMsg  = String.format("Tramite seguente codice prenotazione: %s non abbiamo ottenuto nessuna prenotazione",prenotazione.getCodicePrenotazione());

			log.warning(ErrMsg);

			throw new NotFoundException(ErrMsg);
		}

		if(pre.getGiocatore1()!= null) {

			giocatori.add(pre.getGiocatore1());

		}else if(pre.getGiocatore2()!=null) {

			giocatori.add(pre.getGiocatore2());

		}else if(pre.getGiocatore3()!=null) {

			giocatori.add(pre.getGiocatore3());

		}else if (pre.getGiocatore4()!=null) {

			giocatori.add(pre.getGiocatore4());
		}
		int y = 0;
		
		for (int i = 0; i <giocatori.size();i++) {


			for (y=1+1; y <giocatori.size();y++) {

				if(giocatori.get(i).equals(giocatori.get(y)) ) {

					String ErrMsg  = String.format("Giocatore duplicato nella lista dei giocatori",giocatori.get(i));
					
					log.warning(ErrMsg);
					
					throw new DuplicateException(ErrMsg);
				}



			}
	
		}
    return giocatori;
	}
}
