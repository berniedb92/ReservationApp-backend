package it.bernie.prenotazione.webservice.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "disponibilita_campo")
public class DisponibilitaCampo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public long  id;
	
	@ManyToOne
	@JoinColumn(name = "campo", referencedColumnName = "numero")
	public Campo campo;
	
	@Column(name="giorno")
	public LocalDate giorno;
	
	@Column(name="disponibilita")
	public String disponibilita;

}
