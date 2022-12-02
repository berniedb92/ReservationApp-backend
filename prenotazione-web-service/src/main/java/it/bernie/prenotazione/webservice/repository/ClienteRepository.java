package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer> {	
	
	@Query(value = "SELECT * FROM cliente WHERE codice_fiscale LIKE :cf", nativeQuery = true)
	public Cliente selByCF(@Param("cf") String cf);
	
}
