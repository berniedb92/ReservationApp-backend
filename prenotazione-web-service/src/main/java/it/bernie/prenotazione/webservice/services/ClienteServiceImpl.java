package it.bernie.prenotazione.webservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.repository.ClienteRepository;

@Service
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteRepository repoCliente;
	
	private static <T> List<T> toList(final Iterable<T> iterable) {
	    return StreamSupport.stream(iterable.spliterator(), false)
	                        .toList();
	}
	
	@Override
	public List<Cliente> selTutti() {
		
		Iterable<Cliente> cliente = repoCliente.findAll();
		
		List<Cliente> clienti = toList(cliente);
		return clienti;
		
	}

	@Override
	@Transactional
	public void insCliente(Cliente cliente) {
		
		repoCliente.save(cliente);
		
	}

	@Override
	@Transactional
	public void delCliente(Cliente cliente) {
		
		repoCliente.delete(cliente);
		
	}

	@Override
	public Cliente selByCf(String cf) {
		
		return repoCliente.selByCF(cf);
		
	}

	@Override
	public Optional<Cliente> selById(Integer id) {
		
		return repoCliente.findById(id);
		
	}
	

}
