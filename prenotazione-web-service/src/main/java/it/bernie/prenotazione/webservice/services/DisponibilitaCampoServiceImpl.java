package it.bernie.prenotazione.webservice.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;
import it.bernie.prenotazione.webservice.repository.DisponibilitaCampoRepository;

@Service
@Transactional(readOnly = true)
public class DisponibilitaCampoServiceImpl implements DisponibilitaCampoService{
	
	@Autowired
	DisponibilitaCampoRepository dispCampoRepo ;

	@Override
	public List<DisponibilitaCampo> selTutti() {
		return dispCampoRepo.findAll();
	}

	@Override
	public List<DisponibilitaCampo> selByCampo(Integer campo, String giorno) {
		return dispCampoRepo.selByNumeroCampo(campo, giorno);
	}

}
