package it.unisa.thespoon.ordini;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Ristorante;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia OrdineObserver che costituisce il Subscriber, dichiara il metodo
 * onOrdineStatoChanged che permette di passare i dettagli dell'evento (l'ordine)
 * da notificare.
 */
public interface OrdineObserver {
    void onOrdineStatoChanged(Ordine ordine, Ristorante ristorante);
}
