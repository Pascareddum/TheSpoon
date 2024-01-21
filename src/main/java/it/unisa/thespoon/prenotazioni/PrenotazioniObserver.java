package it.unisa.thespoon.prenotazioni;

import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.model.entity.Ristorante;

/**
 * Interfaccia OrdineObserver che costituisce il Subscriber, dichiara il metodo
 * onPrenotazioneStatoChanged che permette di passare i dettagli dell'evento (la prenotazione)
 * da notificare.
 * @author Jacopo Gennaro Esposito
 */
public interface PrenotazioniObserver {
    void onPrenotazioneStatoChanged(Prenotazione prenotazione, Ristorante ristorante);

}
