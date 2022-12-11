package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tesseramento")
@Data
public class Tesseramento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5158328650803061738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	//@OneToOne(mappedBy = "tessera", cascade = CascadeType.ALL, orphanRemoval = true)
	@ManyToOne
	@JoinColumn(name = "cliente", referencedColumnName = "id")
	private Cliente clienteTess;
	
	@Column(name = "scadenza_certificato")
    private Date scadenzaCertificato;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente")
	@JsonBackReference
	private Set<DettagliPrenotazione> dettCliente  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore1")
	@JsonBackReference(value = "giocatore1")
	private Set<Tesseramento> giocatore1  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore2")
	@JsonBackReference(value = "giocatore2")
	private Set<Tesseramento> giocatore2  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore3")
	@JsonBackReference(value = "giocatore3")
	private Set<Tesseramento> giocatore3  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore4")
	@JsonBackReference(value = "giocatore4")
	private Set<Tesseramento> giocatore4  =new HashSet<>();
	
	
	public Tesseramento() {
		
	}
}
