package com.gestuser.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestuser.webapp.models.Utenti;
import com.gestuser.webapp.repository.UtentiRepository;

@Service
@Transactional()
public class UtentiServiceImpl implements UtentiService {
	
	@Autowired
	UtentiRepository utentiRepo;

	@Override
	public Utenti findByUserId(String Userid) {
		
		return utentiRepo.findByUserId(Userid);
		
	}

	@Override
	public void InsUtente(Utenti utente) {
		
		utentiRepo.save(utente);
		
	}

	@Override
	public void DelUtente(Utenti utente) {
		
		utentiRepo.delete(utente);
		
	}

	@Override
	public List<Utenti> SelTutti() {
		
		return utentiRepo.findAll();
		
	}

}
