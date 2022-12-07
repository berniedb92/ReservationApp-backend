package it.bernie.prenotazione.webservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.DettagliPrenotazione;

public interface DettagliPrenotazioneRepository extends JpaRepository<DettagliPrenotazione, Integer> {
	@Query(value = "SELECT * FROM dettagli_prenotazione WHERE codice =:codPre", nativeQuery = true)
	public List<DettagliPrenotazione> selByCodicePrenotazione(@Param("codPre") Integer codPre);


	@Query(value = "SELECT * FROM dettagli_prenotazione WHERE codice_prenotazione =:codPre AND codice_prenotazione =:cliente", nativeQuery = true)
	public DettagliPrenotazione selByCodicePrenotazioneAndCliente(@Param("codPre") Integer codPre,
			@Param("cliente") Integer cliente);

}
