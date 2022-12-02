/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bernie.prenotazione.webservice.entity;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 *
 * @author berni
 */

@Entity
@Table(name = "campo")
@Data
public class Campo {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "numero")
    private byte numero;
	
	@Column(name = "superficie")
    private String superficie;
    
	@Column(name = "sport")
    private String sport;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campo")
	@JsonBackReference(value = "campoo")
	private Set<Prenotazione> prenotazione = new HashSet<>();
    
}
