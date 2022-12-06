package it.bernie.prenotazione.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.bernie.prenotazione.webservice.entity.Sport;

public interface SportRepository extends JpaRepository<Sport, Integer> {

}
