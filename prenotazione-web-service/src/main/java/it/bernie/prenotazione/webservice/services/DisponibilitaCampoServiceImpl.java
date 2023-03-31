package it.bernie.prenotazione.webservice.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;
import it.bernie.prenotazione.webservice.repository.DisponibilitaCampoRepository;

@Service
@Transactional()
public class DisponibilitaCampoServiceImpl implements DisponibilitaCampoService{
	
	@Autowired
	DisponibilitaCampoRepository dispCampoRepo ;

	@Override
	public List<DisponibilitaCampo> selTutti() {
		
		Iterable<DisponibilitaCampo> iterable = dispCampoRepo.findAll(); 
		List<DisponibilitaCampo> result = new ArrayList<DisponibilitaCampo>();
		iterable.forEach(result::add);
		
		return result;
	}

	@Override
	public List<DisponibilitaCampo> selByCampoDate(Integer campo, Date giorno) {
		return dispCampoRepo.selByNumeroCampoDate(campo, giorno);
	}

	@Override
	public void insDisp(DisponibilitaCampo disponiblita) {
		this.dispCampoRepo.save(disponiblita);
	}

	@Override
	public List<DisponibilitaCampo> selByCampo(Integer campo) {
		return dispCampoRepo.selByNumeroCampo(campo);
	}

}
