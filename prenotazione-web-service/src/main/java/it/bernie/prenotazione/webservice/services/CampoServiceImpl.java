package it.bernie.prenotazione.webservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.Campo;
import it.bernie.prenotazione.webservice.repository.CampoRepository;

@Service
@Transactional(readOnly = true)
public class CampoServiceImpl implements CampoService {
	
	@Autowired
	CampoRepository repoCampo;

	@Override
	public List<Campo> selTutti() {
		
		return repoCampo.findAll();
		
	}

	@Override
	public void insCampo(Campo campo) {
		
		repoCampo.save(campo);
		
	}

	@Override
	public void delCampo(Campo campo) {
		
		repoCampo.delete(campo);
		

	

}

	@Override
	public Campo selCampoById(Integer id) {
		return repoCampo.selByIdCampo(id);
	}
}