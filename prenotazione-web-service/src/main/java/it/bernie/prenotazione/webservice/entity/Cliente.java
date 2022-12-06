/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 *
 * @author berni
 */

@Entity
@Table(name = "cliente")
@Data
public class Cliente implements Serializable {
    
	private static final long serialVersionUID = -6346732724512225289L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome")
    private String nome;
    
	@Column(name = "cognome")
    private String cognome;
    
	@Column(name = "data_nascita")
    private LocalDate dataNascita;
    
	@Column(name = "luogo_nascita")
    private String luogoNascita;
     
	@Column(name = "codice_fiscale")
    private String codiceFiscale;
    
	@Column(name = "nazionalita")
    private String nazionalita;
    
	@Column(name = "indirizzo")
    private String indirizzo;
    
	@Column(name = "email")
    private String email;
    
	@Column(name = "num_telefono")
    private String numTelefono;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
	@JsonBackReference(value = "cliente")
	private List<Prenotazione> prenotazione = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "clienteTess")
	@JsonBackReference(value = "tessera")
	private Tesseramento tessera ;
    
}
