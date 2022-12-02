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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
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
    
	@ManyToOne
	@JoinColumn(name = "cliente", referencedColumnName = "id")
    private Cliente cliente;
    
	@ManyToOne
	@JoinColumn(name = "campo", referencedColumnName = "numero")
    private Campo campo;
    
}
