package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

import it.bernie.prenotazione.webservice.security.Utenti;
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
	
	@Column(name="userid")
	private String userId;
	
	@Column(name = "data_tesseramento")
	private LocalDate dataTesseramento;

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
    private LocalDate scadenzaCertificato;
	
	@Column(name = "scadenza_tessera")
    private LocalDate scadenzaTessera;
	
	@Column(name = "attiva")
    private boolean attiva;
	
	@Column(name="pagamento")
	private Integer pagamento;
	
	@Column(name = "note")
    private String note;
	
	@Column(name = "pagata")
    private boolean pagata;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente")
	@JsonBackReference
	private Set<DettagliPrenotazione> dettCliente  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore1", cascade = CascadeType.ALL)
	@JsonBackReference(value = "giocatore1")
	private Set<Tesseramento> giocatore1  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore2", cascade = CascadeType.ALL)
	@JsonBackReference(value = "giocatore2")
	private Set<Tesseramento> giocatore2  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore3", cascade = CascadeType.ALL)
	@JsonBackReference(value = "giocatore3")
	private Set<Tesseramento> giocatore3  =new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "giocatore4", cascade = CascadeType.ALL)
	@JsonBackReference(value = "giocatore4")
	private Set<Tesseramento> giocatore4  =new HashSet<>();
	
	
	public Tesseramento() {
		
	}
}
