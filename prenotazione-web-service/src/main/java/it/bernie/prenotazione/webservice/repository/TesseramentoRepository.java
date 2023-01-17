package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.Tesseramento;

import java.util.List;

public interface TesseramentoRepository extends JpaRepository<Tesseramento, Integer> {
	
	@Query(value = "SELECT * FROM tesseramento t WHERE cliente=(:id)", nativeQuery = true)
	public Tesseramento selByCliente(@Param("id") Integer id);

	@Query(value = "SELECT * FROM tesseramento INNER JOIN cliente ON cliente.id = tesseramento.cliente WHERE cliente.cognome LIKE CONCAT('%',:cognome,'%') AND tesseramento.attiva =true", nativeQuery = true)
	public List<Tesseramento> selByClienteCognome(@Param("cognome") String cognome);

}
