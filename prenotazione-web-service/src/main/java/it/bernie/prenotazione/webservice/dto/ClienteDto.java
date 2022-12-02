package it.bernie.prenotazione.webservice.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ClienteDto {
	
	private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String luogoNascita;
    private String codiceFiscale;
    private String nazionalita;
    private Date scadenzaCertificato;
    private String indirizzo;
    private String email;
    private String numTelefono;

}
