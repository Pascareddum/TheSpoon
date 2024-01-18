package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.model.entity.Menu;
import it.unisa.thespoon.model.entity.Prodotto;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Tavolo;
import it.unisa.thespoon.model.request.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;

/**
 Interfaccia per i metodi del sottosistema ristorante
 @author Jacopo Gennaro Esposito
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
     * @return ResponseEntity ResponseEntity contenente la lista dei ristoranti associati
     */
    ResponseEntity<Set<Ristorante>> getAllRistorantiByRistoratore(Integer idRistoratore);

    /**
     * Firma del metodo per recuperare i dettagli di un ristorante
     *
     * @param idRistorante rappresenta l'id del ristorante per il quale si vuole recuperarne i dettagli
     * @return ResponseEntity ResponseEntity contenente i dettagli del ristorante
     */
    ResponseEntity<Ristorante> getRistoranteByID(Integer idRistorante);

    /**
     * Firma del metodo per recuperare i ristoranti dato una serie di parametri in input
     *
     * @param searchRistoranteRequest rappresenta la richiesta di ricerca di un ristorante
     * @param nomeRistorante nome del ristorante da cercare
     * @return ResponseEntity ResponseEntity contenente la lista dei ristoranti trovati
     */
    ResponseEntity<Set<Ristorante>> searchRistorante(SearchRistoranteRequest searchRistoranteRequest, String nomeRistorante);

    /**
     * Firma del metodo per aggiungere un menu
     *
     * @param insertMenuRequest rappresenta la richiesta di inserimento di un menu
     * @param email Email associata al proprietario del ristorante
     * @return HttpStatusCode ResponseEntity Codice di stato http
     */
    ResponseEntity<HttpStatus> insertMenu(InsertMenuRequest insertMenuRequest, String email);

    /**
     * Firma del metodo per aggiungere un prdotto ad un menu
     *
     * @param idMenu ID del menu per il quale si intende aggiungere un prodotto
     * @param idProdotto ID del prodotto da aggiungere
     * @param idRistorante ID del ristorante
     * @return HttpStatusCode ResponseEntity Codice di stato http
     */
    ResponseEntity<HttpStatus> addProductToMenu(Integer idMenu, Integer idProdotto, Integer idRistorante, String name);

    /**
     * Firma del metodo per rimuovere un prodotto ad un menu
     *
     * @param idMenu ID del menu per il quale si intende rimuovere un prodotto
     * @param idProdotto ID del prodotto da rimuovere
     * @param idRistorante ID del ristorante
     * @return HttpStatusCode ResponseEntity Codice di stato http
     */
    ResponseEntity<HttpStatus> removeProductFromMenu(Integer idMenu, Integer idProdotto, Integer idRistorante, String mail);

    /**
     * Firma del metodo per ottenere i menu associati ad un ristorante
     *
     * @param ID Id del ristorante per il quale si vuole recuperare la lista dei menu
     * @return ResponseEntity ResponseEntity contenente la lista dei menu associati
     */
    ResponseEntity<Set<Menu>> getMenusByRistoranteID(Integer ID);

    /**
     * Firma del metodo per ottenere il menu associato ad un dato id
     *
     * @param idMenu Id del ristorante per il quale si vuole recuperare la lista dei menu
     * @return ResponseEntity ResponseEntity contenente la lista dei menu associati
     */
    ResponseEntity<Menu> getMenusByID(Integer idMenu);

    /**
     * Firma del metodo per ottenere i prodotti associati ad un dato menu
     *
     * @param idMenu Id del menu per il quale si vuole recuperare la lista dei prodotti
     * @return ResponseEntity ResponseEntity contenente la lista dei prodotti associati
     */
    ResponseEntity<Set<Prodotto>> getProdottiByMenuID(Integer idMenu);

    /**
     * Firma del metodo per inserire un nuovo tavolo
     *
     * @param insertTavoloRequest Oggetto che rappresenta una richiesta di inserimento
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     **/
    ResponseEntity<HttpStatus> insertTavolo(InsertTavoloRequest insertTavoloRequest, String email);

    /**
     * Firma del metodo per recuperare i tavoli associati ad un ristorante
     *
     * @param idRistorante ID del ristorante per il quale si vuole ottenere la lista dei tavoli
     * @return ResponseEntity Set contenente la lista dei tavoli
     **/
    ResponseEntity<Set<Tavolo>> getTavoliRistorante(Integer idRistorante);

    /**
     * Firma del metodo per recuperare i dettagli di un tavolo dato il suo ID
     *
     * @param idTavolo ID del ristorante per il quale si vuole ottenere la lista dei tavoli
     * @param idRistorante ID del ristorante
     * @return ResponseEntity Response entity contenente i dettagli del tavolo
     **/
    ResponseEntity<Tavolo> getTavoloByID(String idTavolo, Integer idRistorante);
}
