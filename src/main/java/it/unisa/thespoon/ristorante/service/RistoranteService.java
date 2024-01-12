package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import it.unisa.thespoon.model.request.SearchRistoranteRequest;
import it.unisa.thespoon.model.request.UpdateRistoranteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;

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

    /**
     * Firma del metodo per aggiornare i dettagli di un ristorante
     *
     * @param updateRistoranteRequest rappresenta la richiesta di modifica dei dettagli del ristorante
     * @param idRistorante Id univoco del ristorante di cui mondificare i dati
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     */
    ResponseEntity<HttpStatus> updateRistorante(UpdateRistoranteRequest updateRistoranteRequest, Integer idRistorante, String email);

    /**
     * Firma del metodo per recuperare tutti i ristoranti associati ad un dato ristoratore
     *
     * @param idRistoratore rappresenta l'id del ristoratore per il quale si vuole recuperare la lista dei ristoranti
     * @return ResponseEntity<Set<Ristorante>> ResponseEntity contenente la lista dei ristoranti associati
     */
    ResponseEntity<Set<Ristorante>> getAllRistorantiByRistoratore(Integer idRistoratore);

    /**
     * Firma del metodo per recuperare i dettagli di un ristorante
     *
     * @param idRistorante rappresenta l'id del ristorante per il quale si vuole recuperarne i dettagli
     * @return ResponseEntity<Ristorante> ResponseEntity contenente i dettagli del ristorante
     */
    ResponseEntity<Ristorante> getRistoranteByID(Integer idRistorante);

    /**
     * Firma del metodo per recuperare i ristoranti dato una serie di parametri in input
     *
     * @param searchRistoranteRequest rappresenta la richiesta di ricerca di un ristorante
     * @param nomeRistorante nome del ristorante da cercare
     * @return ResponseEntity<Set<Ristorante>> ResponseEntity contenente la lista dei ristoranti trovati
     */
    ResponseEntity<Set<Ristorante>> searchRistorante(SearchRistoranteRequest searchRistoranteRequest, String nomeRistorante);
}
