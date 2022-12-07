/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
	
	@Column(name ="codice_prenotazione")
	private int codicePrenotazione;
    
	@ManyToOne
	@JoinColumn(name = "cliente", referencedColumnName = "id")
    private Cliente cliente;
    
	@ManyToOne
	@JoinColumn(name = "campo", referencedColumnName = "numero")
    private Campo campo;
    
	@Column(name = "giocatore1")
    private Integer giocatore1;
	
	@Column(name = "giocatore2")
    private Integer giocatore2;
	
	@Column(name = "giocatore3")
    private Integer giocatore3;
	
	@Column(name = "giocatore4")
    private Integer giocatore4;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "codicePrenotazione")
	@JsonBackReference
	private Set<DettagliPrenotazione> codicePrenotazioni  =new HashSet<>();
	

	
   
}
