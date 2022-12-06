package it.bernie.prenotazione.webservice.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "tipo_tessera")
@Data
public class TipoTessera {
		
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "quota_associativa")
	private Integer quotaAssociativa;
		
	@Column(name = "tipo")
	private String tipo;
		
	@Column(name = "prezzo")
	private Integer prezzo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipo")
	@JsonBackReference
	private Set<Tesseramento> tesseramento = new HashSet<>();
	
}
