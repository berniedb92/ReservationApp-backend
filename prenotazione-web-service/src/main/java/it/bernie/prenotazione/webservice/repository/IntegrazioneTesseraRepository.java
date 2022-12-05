package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bernie.prenotazione.webservice.entity.IntegrazioneTessera;

public interface IntegrazioneTesseraRepository extends JpaRepository<IntegrazioneTessera, Integer> {

}
