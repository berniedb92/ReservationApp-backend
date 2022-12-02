package it.bernie.prenotazione.webservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "integrazione_tessera")
@Data
public class IntegrazioneTessera {
		
	@Id
	@Column(name = "id")
	private Integer id;
			
	@Column(name = "tipo_integrazione")
	private String tipo;
			
	@Column(name = "prezzo")
	private Integer prezzo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "integrazione")
	@JsonBackReference
	private Set<Tesseramento> tesseramento  =new HashSet<>();
	
	
}
