package it.bernie.prenotazione.webservice.utility;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.exceptions.DuplicateException;
import it.bernie.prenotazione.webservice.exceptions.NotFoundException;
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

	private static final String FORMATDATA = "yyyy-MM-dd";

	private static final String FormatOra = "HH:mm";

	public List<Cliente> clientiNonTesserati(List<Cliente> clienti) {
		List<Cliente> clientiTess = new ArrayList<>();

		clientiTess.addAll(clienti);
		List<Tesseramento> tesseramenti = servTess.selTutti();

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

		Prenotazione pre = servPre.selByCodicePrenot(prenotazione.getCodicePrenotazione());

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

					String ErrMsg = String.format("Giocatore  duplicato nella lista dei giocatori", giocatori.get(i));

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

		String data = df.format(prenotazione.getData());

		List<Prenotazione> listPre = servPre.selByData(data);
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
			if (oraInizio.equals(df3.format(prenotazione.getOraInizio())) && listPre.get(i).getCampo().getNumero() == prenotazione.getCampo().getNumero()) {

				String errMsg = String.format(
						"Impossibile prenotare perche l'orario delle:%s scelto ha gia una prenotazione",
						df2.format(prenotazione.getOraInizio()));

				log.warning(errMsg);

				throw new DuplicateException(errMsg);

			}

		}

	}
}
