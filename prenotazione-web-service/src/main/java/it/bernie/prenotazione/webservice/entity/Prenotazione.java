/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 *
 * @author berni
 */

@Entity
@Table(name = "prenotazione")
@Data
public class Prenotazione implements Serializable {
	
	private static final long serialVersionUID = 2197198571851984984L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "data")
    private Date data;
    
	@Column(name = "ora_inizio")
    private Date oraInizio;
	
	@Column(name = "ora_fine")
    private Date oraFine;
    
	@Column(name = "modalita")
    private String modalita;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="codice_prenotazione")
	private int codicePrenotazione;
    
	@ManyToOne
	@JoinColumn(name = "campo", referencedColumnName = "numero")
    private Campo campo;
    
	@ManyToOne
	@JoinColumn(name = "giocatore1", referencedColumnName = "codice_tessera")
	//@Column(name = "giocatore1")
    private Tesseramento giocatore1;
	
	@ManyToOne
	@JoinColumn(name = "giocatore2", referencedColumnName = "codice_tessera")
	//@Column(name = "giocatore2")
    private Tesseramento giocatore2;
	
	@ManyToOne
	@JoinColumn(name = "giocatore3", referencedColumnName = "codice_tessera", nullable = true)
	//@Column(name = "giocatore3")
    private Tesseramento giocatore3;
	
	@ManyToOne
	@JoinColumn(name = "giocatore4", referencedColumnName = "codice_tessera", nullable = true)
	//@Column(name = "giocatore4")
    private Tesseramento giocatore4;
	
	@Column(name = "evento")
    private String evento;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "codicePrenotazione")
	@JsonBackReference
	private Set<DettagliPrenotazione> codicePrenotazioni  =new HashSet<>();
	

	
   
}
