package it.bernie.prenotazione.webservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.Campo;
import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;

public interface CampoRepository extends JpaRepository<Campo, Integer> {

		@Query(value = "SELECT * FROM campo WHERE id =:id", nativeQuery = true)
		public Campo selByIdCampo(@Param("id") Integer id);
}
