package it.bernie.prenotazione.webservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.websocket.OnError;

import lombok.Data;


@Entity
@Table(name = "dettagli_prenotazione")
@Data
public class DettagliPrenotazione implements Serializable {
 
	private static final long serialVersionUID = 7630555811128299657L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dettaglio")
	private Integer idDettaglio;
	
	
	@ManyToOne
	@JoinColumn(name = "codice", referencedColumnName = "codice_prenotazione")
	 private Prenotazione codicePrenotazione;
	
	@ManyToOne
	@JoinColumn(name="tesserato", referencedColumnName = "codice_tessera")
	private Tesseramento cliente;
	
	
	@Column(name="pagamento")
	private float pagamento;
	
	@Column(name = "pagamento_effettuato")
	private boolean pagamentoEffettuato;
	
	@Column(name ="note")
	private String note;
	
	public DettagliPrenotazione(Prenotazione pre,Tesseramento cli,float pag, boolean pagEff) {
	
		this.codicePrenotazione = pre;
		this.cliente = cli;
		this.pagamento = pag;
		this.pagamentoEffettuato = pagEff;
	}

	public DettagliPrenotazione() {
		super();
	}
}
