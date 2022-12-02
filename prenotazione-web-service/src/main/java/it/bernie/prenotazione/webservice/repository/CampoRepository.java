package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.bernie.prenotazione.webservice.entity.Campo;

public interface CampoRepository extends JpaRepository<Campo, Integer> {

}
