package it.bernie.prenotazione.webservice.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.repository.TesseramentoRepository;
import it.bernie.prenotazione.webservice.utility.UtilityCalcolo;

@Service
@Transactional(readOnly = true)
public class TesseramentoServiceImpl implements TesseramentoService {
	
	@Autowired
	TesseramentoRepository repoTess;
	
	@Autowired
	UtilityCalcolo calcolo;

	@Override
	public Tesseramento selByCodiceTessera(Integer codiceTessera) {
		
		Optional<Tesseramento> tessOpt = repoTess.findById(codiceTessera);
		Tesseramento tesseramento = null;
		
		if(!tessOpt.isEmpty()) {
			tesseramento = tessOpt.get();
		}
		
		return tesseramento;
	}

	@Override
	public List<Tesseramento> selTutti() {
		
		return repoTess.findAll();
	}

	@Override
	@Transactional
	public void insTessera(Tesseramento t) {
		
		LocalDate dataAttivazione = LocalDate.now();

		if(t.getScadenzaCertificato().isBefore(dataAttivazione) || t.getScadenzaCertificato() == null) {
			
			t.setAttiva(false);
			t.setDataTesseramento(dataAttivazione);
			
		} else {
			
			t.setAttiva(true);
			t.setDataTesseramento(dataAttivazione);
			LocalDate dataScadenza = LocalDate.of(dataAttivazione.getYear(), 12, 31);
			t.setScadenzaTessera(dataScadenza);
			t.setPagamento(calcolo.getQuotaTessera(t));
			t.setPagata(false);
			
		}
		
		repoTess.save(t);
		
	}

	@Override
	public Tesseramento selByClienteId(Integer id) {
		
		return repoTess.selByCliente(id);
	}

	@Override
	public List<Tesseramento> selByCognome(String cognome) {
		return repoTess.selByClienteCognome(cognome);
	}

	@Transactional
	@Override
	public void deleteTessera(Tesseramento t) {
		
		repoTess.delete(t);
		
	}

}
