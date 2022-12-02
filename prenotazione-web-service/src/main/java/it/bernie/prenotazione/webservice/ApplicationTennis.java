package it.bernie.prenotazione.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




//import it.accademia.jpr.libro.model.model_jdbc.JdbcConnection;


@SpringBootApplication
public class ApplicationTennis {

    private static final Logger LOGGER=LoggerFactory.getLogger(ApplicationTennis.class);

    public static void main(String[] args) {
        
        SpringApplication.run(it.bernie.prenotazione.webservice.ApplicationTennis.class, args);
        
//        JdbcConnection.initJdbcConnectionProperties("libri", "root", "");       //inizializza i valori per la connessione al database (databasename, username, password)
    }

}
