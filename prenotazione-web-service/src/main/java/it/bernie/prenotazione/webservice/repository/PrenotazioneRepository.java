package it.bernie.prenotazione.webservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.Cliente;
import it.bernie.prenotazione.webservice.entity.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
	
	@Query(value = "SELECT * FROM prenotazione WHERE data =:data", nativeQuery = true)
	public List<Prenotazione> selByData(@Param("data") Date data);

	
	@Query(value ="SELECT * FROM prenotazione WHERE codice_prenotazione =:codPre",nativeQuery = true)
	public Prenotazione selByCodicePrenotazione(@Param("codPre") Integer codPrenotazione);
	
	@Query(value ="SELECT * FROM prenotazione WHERE data =:data",nativeQuery = true)
	public List<Prenotazione> selPrenotazioniByData(@Param("data") String data);

	@Query(value ="SELECT * FROM prenotazione WHERE data=:data AND campo=:campo",nativeQuery = true)
	public List<Prenotazione> selPrenotazioniByDataAndCampo(@Param("data") Date data, @Param("campo") Integer campo);
}
