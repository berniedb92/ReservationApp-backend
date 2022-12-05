package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bernie.prenotazione.webservice.entity.TipoTessera;

public interface TipoTesseraRepository extends JpaRepository<TipoTessera, Integer> {

}
