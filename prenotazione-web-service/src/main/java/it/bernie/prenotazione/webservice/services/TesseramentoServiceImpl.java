package it.bernie.prenotazione.webservice.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.repository.TesseramentoRepository;

@Service
@Transactional(readOnly = true)
public class TesseramentoServiceImpl implements TesseramentoService {
	
	@Autowired
	TesseramentoRepository repoTess;

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
		
		Date dataAttivazione = Date.from(Instant.now());

		if(t.getScadenzaCertificato().before(dataAttivazione) || t.getScadenzaCertificato() == null) {
			
			t.setAttiva(false);
			t.setDataTesseramento(dataAttivazione);
			
		} else {
			
			t.setAttiva(true);
			t.setDataTesseramento(dataAttivazione);
			Date dataScadenza = Date.from(Instant.now());
			dataScadenza.setDate(31);
			dataScadenza.setMonth(11);
			dataScadenza.setYear(dataAttivazione.getYear());
			t.setScadenzaTessera(dataScadenza);
			
		}
		
		repoTess.save(t);
		
	}

	@Override
	public Tesseramento selByClienteId(Integer id) {
		
		return repoTess.selByCliente(id);
	}

}
