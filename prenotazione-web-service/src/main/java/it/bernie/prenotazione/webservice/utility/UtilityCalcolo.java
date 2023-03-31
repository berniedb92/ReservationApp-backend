package it.bernie.prenotazione.webservice.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.bernie.prenotazione.webservice.ApplicationCostants;
import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.repository.ClienteRepository;
import it.bernie.prenotazione.webservice.services.TesseramentoService;

public class UtilityCalcolo {
	
	public List<Cliente> compleanni(List<Cliente> clienti) {
		
		List<Cliente> clientiComplex = new ArrayList<>();
		
		LocalDate dataOggi = LocalDate.now();
		
		
		for(int i = 0; i < clienti.size(); i++) {
			
			if(clienti.get(i).getDataNascita().getMonth()== dataOggi.getMonth()) {
				if(clienti.get(i).getDataNascita().getDayOfMonth()== dataOggi.getDayOfMonth()) {
					clientiComplex.add(clienti.get(i));
				}
			}
			
		}
		
		return clientiComplex;
		
	}
	
	public Integer getQuotaTessera(Tesseramento tesseramento) {
		
		Integer calcoloTessera =  tesseramento.getIntegrazione().getPrezzo() 
				+ tesseramento.getTipo().getQuotaAssociativa() 
				+ tesseramento.getTipo().getPrezzo();
		
		return calcoloTessera;
	}
	
	public float gestioneQuote(Tesseramento tesseramento, Prenotazione prenotazione) {
		
		float quota = 0f;
		
		String modalita = prenotazione.getModalita();
		
		String tipoTessera = tesseramento.getTipo().getTipo();
		
		if(tipoTessera.length() > 4) {
			
			String[] split = tipoTessera.split("/");
			
			if(split[0].contains("SUPER-TENNIS") && split[1].contains("BASE-PADEL")) {
				
				if(modalita.equalsIgnoreCase("singolo")) {
					quota = ApplicationCostants.SINGOLO_TENNIS_SUPER;
				} else if(modalita.equalsIgnoreCase("doppio")) {
					quota = ApplicationCostants.DOPPIO_TENNIS_SUPER;
				} else {
					quota = ApplicationCostants.PADEL_BASE;
				}
				
			} else if(split[0].contains("BASE-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				if(modalita.equalsIgnoreCase("singolo")) {
					quota = ApplicationCostants.SINGOLO_TENNIS_BASE;
				} else if(modalita.equalsIgnoreCase("doppio")) {
					quota = ApplicationCostants.DOPPIO_TENNIS_BASE;
				} else {
					quota = ApplicationCostants.PADEL_SUPER;
				}
				
			} else if(split[0].contains("SUPER-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				if(modalita.equalsIgnoreCase("singolo")) {
					quota = ApplicationCostants.SINGOLO_TENNIS_SUPER;
				} else if(modalita.equalsIgnoreCase("doppio")) {
					quota = ApplicationCostants.DOPPIO_TENNIS_SUPER;
				} else {
					quota = ApplicationCostants.PADEL_SUPER;
				}
				
			}
			
		} else {
				
			if(modalita.equalsIgnoreCase("singolo")) {
				quota = ApplicationCostants.SINGOLO_TENNIS_BASE;
			} else if(modalita.equalsIgnoreCase("doppio")) {
				quota = ApplicationCostants.DOPPIO_TENNIS_BASE;
			} else {
				quota = ApplicationCostants.PADEL_BASE;
			}
			
		}
		
		return quota;
		
	}
	
	public Map<String, Float> panoramicaQuote(Tesseramento tesseramento) {
		
		Map<String, Float> quota = new HashMap<>();
		
		String tipoTessera = tesseramento.getTipo().getTipo();
		
		if(tipoTessera.length() > 4) {
			
			String[] split = tipoTessera.split("/");
			
			if(split[0].contains("SUPER-TENNIS") && split[1].contains("BASE-PADEL")) {
				
				quota.put("SingoloTennis",ApplicationCostants.SINGOLO_TENNIS_SUPER);
				
				quota.put("DoppioTennis",ApplicationCostants.DOPPIO_TENNIS_SUPER);
				 
				quota.put("Padel",ApplicationCostants.PADEL_BASE);
								
			} else if(split[0].contains("BASE-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				quota.put("SingoloTennis",ApplicationCostants.SINGOLO_TENNIS_BASE);
				
				quota.put("DoppioTennis",ApplicationCostants.DOPPIO_TENNIS_BASE);
				
				quota.put("Padel",ApplicationCostants.PADEL_SUPER);
							
			} else if(split[0].contains("SUPER-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
		
				quota.put("SingoloTennis",ApplicationCostants.SINGOLO_TENNIS_SUPER);
				
				quota.put("DoppioTennis",ApplicationCostants.DOPPIO_TENNIS_SUPER);
				
				quota.put("Padel", ApplicationCostants.PADEL_SUPER);
							
			}
			
		} else {
				
			quota.put("SingoloTennis",ApplicationCostants.SINGOLO_TENNIS_BASE);
		
			quota.put("DoppioTennis",ApplicationCostants.DOPPIO_TENNIS_BASE);
			
			quota.put("Padel",ApplicationCostants.PADEL_BASE);		
			
		}
		
		return quota;
		
	}

}
