package it.unisa.thespoon.prenotazioni.service;


import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.model.entity.Tavolo;
import it.unisa.thespoon.model.request.InsertPrenotazioneRequest;
import it.unisa.thespoon.model.request.UpdatePrenotazioneRequest;
import it.unisa.thespoon.model.response.PrenotazioneInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interfaccia per i metodi del sottosistema Prenotazioni
 * @author Jacopo Gennaro Esposito
 */
public interface PrenotazioniService {

    /**
     * Firma del metodo adibito all'inserimento di una nuova prenotazione all'interno di TheSpoon
     * @param insertPrenotazioneRequest Oggetto che rappresenta una richiesta di inserimento di una nuova prenotazione
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    ResponseEntity<Prenotazione> insertPrenotazioen(InsertPrenotazioneRequest insertPrenotazioneRequest);

    /**
     * Firma del metodo adibito alla modifica di una prenotazione all'interno di TheSpoon
     * @param updatePrenotazioneRequest Oggetto che rappresenta una richiesta di modifica di una nuova prenotazione
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    ResponseEntity<Prenotazione> updatePrenotazione(UpdatePrenotazioneRequest updatePrenotazioneRequest);

    /**
     * Firma del metodo per ottenere le prenotazioni di un ristorante dato il suo id
     * @param idRistorante Identificativo del ristorante per il quale si intende recuperare la lista delle prenotazioni
     * @param Email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    ResponseEntity<List<PrenotazioneInfo>> getAllPrenotazioni(Integer idRistorante, String Email);

    /**
     * Firma del metodo per ottenere i tavoli associati ad una prenotazione dato il suo id
     * @param idPrenotazione Identificativo della prenotazione
     * @param Email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    ResponseEntity<List<Tavolo>> getAllTavoliByIDPrenotazione(Integer idPrenotazione, String Email);

    /**
     * Firma del metodo per confermare una prenotazione
     * @param idPrenotazione Identificativo della prenotazione
     * @param Email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    ResponseEntity<HttpStatus> confermaPrenotazione(Integer idPrenotazione, String Email);
}
