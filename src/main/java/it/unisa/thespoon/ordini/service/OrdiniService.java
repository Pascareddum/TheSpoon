package it.unisa.thespoon.ordini.service;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.request.InsertOrdineRequest;
import it.unisa.thespoon.model.response.ProdottoOrdineInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 @author Jacopo Gennaro Esposito
 Interfaccia per i metodi del sottosistema Prodotto
 **/
public interface OrdiniService {

     /**
      * Firma del metodo per inserire un nuovo ordine
      *
      * @param insertOrdineRequest Oggetto che rappresenta una richiesta di inserimento ordine
      * @return ResponseEntity <Ordine> Response contenente i dettagli dell'ordine
      **/
    ResponseEntity<Ordine> insertOrdine(InsertOrdineRequest insertOrdineRequest);


    /**
     * Firma del metodo per confermare un nuovo ordine
     *
     * @param idOrdine ID dell'ordine che si intende confermare
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity <Ordine> Response contenente i dettagli dell'ordine
     **/
    ResponseEntity<HttpStatus> confermaOrdine(Integer idOrdine, String email);

    /**
     * Firma del metodo per ottenere gli ordini associati ad un ristorante
     *
     * @param idOrdine Identificativo dell'ordine
     * @return ResponseEntity <Ordine> Response contenente i dettagli dell'ordine
     **/
    ResponseEntity<List<Ordine>> ordiniByRistorante(Integer idOrdine, String name);

    /**
     * Firma del metodo per ottenere i dettagli dei prodotti
     * associati ad un ordine dato il suo id e l'id del ristorante
     *
     * @param idRistorante Identificativo del ristorante
     * @param idOrdine Identificativo dell'ordine
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity <List<Prodotto> Response contenente i dettagli dei prodotti associati ad un ordine
     * */
    ResponseEntity<List<ProdottoOrdineInfo>> getProdottiByIdOrdineIdRistorante(Integer idRistorante, Integer idOrdine, String email);

    /**
     * Firma del metodo per ottenere i dettagli di un ordine dato il suo ID e l'id del ristorante
     *
     * @param idOrdine Identificativo del ristorante
     * @param idRistorante Identificativo dell'ordine
     * @return Ordine
     */
    Optional<Ordine> getOrdineByIdOrdinedAndIdRistorante(Integer idOrdine, Integer idRistorante);
}
