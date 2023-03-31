package it.bernie.prenotazione.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class ApplicationTennis {

    public static void main(String[] args) {
        SpringApplication.run(it.bernie.prenotazione.webservice.ApplicationTennis.class, args);
    }

}
