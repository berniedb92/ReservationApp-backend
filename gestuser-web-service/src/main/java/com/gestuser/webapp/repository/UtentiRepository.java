package com.gestuser.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gestuser.webapp.models.Utenti;
 
public interface UtentiRepository extends MongoRepository<Utenti, String> 
{
	public Utenti findByUserId(String UserId);
}
