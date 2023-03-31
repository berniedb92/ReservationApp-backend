package com.gestuser.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestuser.webapp.models.Utenti;

public interface UtentiRepository extends JpaRepository<Utenti, Integer> {
	
	@Query(value = "SELECT * FROM users WHERE userid=:userid", nativeQuery = true)
	public Utenti findByUserId(@Param("userid") String userid);
	
}