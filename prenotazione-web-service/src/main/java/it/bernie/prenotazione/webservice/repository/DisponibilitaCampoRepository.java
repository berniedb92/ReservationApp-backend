package it.bernie.prenotazione.webservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.DisponibilitaCampo;

public interface DisponibilitaCampoRepository extends JpaRepository<DisponibilitaCampo, Integer> {
	
	@Query(value = "SELECT * FROM disponibilita_campo WHERE campo=:campo AND giorno=:giorno", nativeQuery = true)
	public List<DisponibilitaCampo> selByNumeroCampo(@Param("campo") Integer campo, @Param("giorno") String giorno);

}
