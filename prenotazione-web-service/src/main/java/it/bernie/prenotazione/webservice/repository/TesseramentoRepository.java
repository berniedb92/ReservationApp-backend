package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.Tesseramento;

public interface TesseramentoRepository extends JpaRepository<Tesseramento, Integer> {
	
	@Query(value = "SELECT * FROM tesseramento t WHERE cliente=(:id)", nativeQuery = true)
	public Tesseramento selByCliente(@Param("id") Integer id);

}
