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

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.Prenotazione;
import it.bernie.prenotazione.webservice.entity.Tesseramento;
import it.bernie.prenotazione.webservice.repository.ClienteRepository;
import it.bernie.prenotazione.webservice.services.TesseramentoService;

@Service
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
					quota = 4.0f;
				} else if(modalita.equalsIgnoreCase("doppio")) {
					quota = 3.0f;
				} else {
					quota = 10.0f;
				}
				
			} else if(split[0].contains("BASE-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				if(modalita.equalsIgnoreCase("singolo")) {
					quota = 7.0f;
				} else if(modalita.equalsIgnoreCase("doppio")) {
					quota = 6.0f;
				} else {
					quota = 7.0f;
				}
				
			} else if(split[0].contains("SUPER-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				if(modalita.equalsIgnoreCase("singolo")) {
					quota = 4.0f;
				} else if(modalita.equalsIgnoreCase("doppio")) {
					quota = 3.0f;
				} else {
					quota = 7.0f;
				}
				
			}
			
		} else {
				
			if(modalita.equalsIgnoreCase("singolo")) {
				quota = 7.0f;
			} else if(modalita.equalsIgnoreCase("doppio")) {
				quota = 6.0f;
			} else {
				quota = 10.0f;
			}
			
		}
		
		return quota;
		
	}
	
	public Map<String, Float> panoramicaQuote(Tesseramento tesseramento, Prenotazione prenotazione) {
		
		Map<String, Float> quota = new HashMap<>();
		
		String modalita = prenotazione.getModalita();
		
		String tipoTessera = tesseramento.getTipo().getTipo();
		
		if(tipoTessera.length() > 4) {
			
			String[] split = tipoTessera.split("/");
			
			if(split[0].contains("SUPER-TENNIS") && split[1].contains("BASE-PADEL")) {
				
				if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("singolo")) {
					quota.put("Singolo-Tennis",4.0f);
				} 
				if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("doppio")) {
					quota.put("Doppio-Tennis",3.0f);
				} 
				
				if(tipoTessera.equals("Padel")) {
					quota.put("Padel",10.0f);
				}
				
			} else if(split[0].contains("BASE-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("singolo")) {
					quota.put("Singolo-Tennis",7.0f);
				} 
				
				if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("doppio")) {
					quota.put("Doppio-Tennis",6.0f);
				} 
				
				if(tipoTessera.equals("Padel")) {
					quota.put("Padel",7.0f);
				}
				
			} else if(split[0].contains("SUPER-TENNIS") && split[1].contains("SUPER-PADEL")) {
				
				if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("singolo")) {
					quota.put("Singolo-Tennis",4.0f);
				} 
				
				if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("doppio")) {
					quota.put("Doppio-Tennis",3.0f);
				} 
				
				if(tipoTessera.equals("Padel")) {
					quota.put("Padel", 7.0f);
				}
				
			}
			
		} else {
				
			if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("singolo")) {
				quota.put("Singolo-Tennis",7.0f);
			} 
			
			if(tipoTessera.equals("Tennis") && modalita.equalsIgnoreCase("doppio")) {
				quota.put("Doppio-Tennis",6.0f);
			} 
			
			if(tipoTessera.equals("Padel")) {
				quota.put("Padel",10.0f);
			}
			
		}
		
		return quota;
		
	}

}
