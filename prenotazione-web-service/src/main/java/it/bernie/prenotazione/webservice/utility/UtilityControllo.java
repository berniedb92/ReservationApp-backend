package it.bernie.prenotazione.webservice.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.services.ClienteService;
import it.bernie.prenotazione.webservice.services.TesseramentoService;

@Service
public class UtilityControllo {
	
	@Autowired
	TesseramentoService servTess;
	
	@Autowired
	ClienteService servCliente;
	
	public List<Cliente> clientiNonTesserati(List<Cliente> clienti) {
		List<Cliente> clientiTess = new ArrayList<>(); 
		
		clientiTess.addAll(clienti);
		List<Tesseramento> tesseramenti = servTess.selTutti();
		
		for(int i =0; i< tesseramenti.size(); i++) {
			
			for(int j =0; j< clientiTess.size(); j++) {
				
				if(clientiTess.get(j).getId() == tesseramenti.get(i).getClienteTess().getId()) {
					
					clientiTess.remove(j);
				
				}
			
			}
		
		}
		
		return clientiTess;
		
	}

}
