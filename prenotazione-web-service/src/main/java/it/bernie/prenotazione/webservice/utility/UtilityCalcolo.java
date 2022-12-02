package it.bernie.prenotazione.webservice.utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.repository.ClienteRepository;

@Service
public class UtilityCalcolo {
	
	public List<Cliente> compleanni(List<Cliente> clienti) {
		
		List<Cliente> clientiComplex = new ArrayList<>();
		
		LocalDate dataOggi = LocalDate.now();
		
		for(int i = 0; i < clienti.size(); i++) {
			
			if(clienti.get(i).getDataNascita().getMonthValue() == dataOggi.getMonthValue()) {
				if(clienti.get(i).getDataNascita().getDayOfYear() == dataOggi.getDayOfYear()) {
					clientiComplex.add(clienti.get(i));
				}
			}
			
		}
		
		return clientiComplex;
		
	}

}
