package it.bernie.prenotazione.webservice.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.repository.ClienteRepository;

@Service
public class UtilityCalcolo {
	
	public List<Cliente> compleanni(List<Cliente> clienti) {
		
		List<Cliente> clientiComplex = new ArrayList<>();
		
		Date dataOggi = Date.from(Instant.now());
		
		
		for(int i = 0; i < clienti.size(); i++) {
			
			if(clienti.get(i).getDataNascita().getMonth()== dataOggi.getMonth()) {
				if(clienti.get(i).getDataNascita().getDay()== dataOggi.getDay()) {
					clientiComplex.add(clienti.get(i));
				}
			}
			
		}
		
		return clientiComplex;
		
	}

}
