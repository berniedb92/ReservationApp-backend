package it.bernie.prenotazione.webservice.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.repository.PrenotazioneRepository;

@Service
@Transactional(readOnly = true)
public class PrenotazioneServiceImpl implements PrenotazioneService {
	
	@Autowired
	PrenotazioneRepository repoPren;

	@Transactional
	@Override
	public void insPrenotazione(Prenotazione prenotazione) {
		// TODO Auto-generated method stub
		
		if(prenotazione.getModalita() == null) {
			prenotazione.setModalita("padel");
		} 
		repoPren.save(prenotazione);
	}

	@Override
	public List<Prenotazione> selTutte() {
		// TODO Auto-generated method stub
		return repoPren.findAll();
	}

	@Override
	public List<Prenotazione> selByData(String data) {
		// TODO Auto-generated method stub
		return repoPren.selByData(data);
	}

	@Override
	public Prenotazione selById(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<Prenotazione> pren = repoPren.findById(id);
		Prenotazione p = null;
		
		if(!pren.isEmpty()) {
			p = pren.get();
		}
		
		return p;
	}

	@Override
	public Prenotazione selByCodicePrenot(Integer codPre) {
		
		return repoPren.selByCodicePrenotazione(codPre);
	}

	@Override
	public List<Prenotazione> selByDataPrenotazione(String data) {
	
		return repoPren.selPrenotazioniByData(data);
	}

}
