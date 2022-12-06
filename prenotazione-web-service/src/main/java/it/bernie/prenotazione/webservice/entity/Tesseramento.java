package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @OneToOne
	@JoinColumn(name = "cliente", referencedColumnName = "id")
	private Cliente clienteTess;
	
	@Column(name = "scadenza_certificato")
    private Date scadenzaCertificato;
	
//	
//	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente")
//	@JsonBackReference
//	private Set<DettagliPrenotazione> dettCliente  =new HashSet<>();
//	
}
