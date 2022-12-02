package it.bernie.prenotazione.webservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tesseramento")
@Data
public class Tesseramento {
	
	@Id
	@Column(name = "codice_tessera")
	private Integer codiceTessera;
	
	@Column(name = "data_tesseramento")
	private Date dataTesseramento;

	@ManyToOne
	@JoinColumn(name = "tipo_tessera", referencedColumnName = "id")
	private TipoTessera tipo;
	
	@ManyToOne
	@JoinColumn(name = "integrazione_tessera", referencedColumnName = "id")
	private IntegrazioneTessera integrazione;
	
	@ManyToOne
	@JoinColumn(name = "cliente", referencedColumnName = "id")
	private Cliente cliente;
	
}