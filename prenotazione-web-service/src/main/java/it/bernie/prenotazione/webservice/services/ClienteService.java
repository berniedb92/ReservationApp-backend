package it.bernie.prenotazione.webservice.services;

import java.util.List;
import java.util.Optional;

import it.bernie.prenotazione.webservice.entity.Cliente;


public interface ClienteService {
	
	public List<Cliente> selTutti();
	
	public Cliente selByCf(String cf);
	
	public Optional<Cliente> selById(Integer id);
	
	public void insCliente(Cliente cliente);
	
	public void delCliente(Cliente cliente);
	
}
