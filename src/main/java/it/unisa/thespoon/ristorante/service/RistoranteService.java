package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 @author Jacopo Gennaro Esposito
 Interfaccia per i metodi del sottosistema ristorante
 **/
public interface RistoranteService {

    /**
     * Firma del metodo per inserire un nuovo ristorante
     *
     * @param insertRistoranteRequest Oggetto che rappresenta una richiesta di inserimento
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     **/
    ResponseEntity<HttpStatus> insertRistorante(InsertRistoranteRequest insertRistoranteRequest, String email);
}
