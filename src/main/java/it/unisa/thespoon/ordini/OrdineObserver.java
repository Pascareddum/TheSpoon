package it.unisa.thespoon.ordini;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Ristorante;

/**
 * Interfaccia OrdineObserver che costituisce il Subscriber, dichiara il metodo
 * onOrdineStatoChanged che permette di passare i dettagli dell'evento (l'ordine)
 * da notificare.
 * @author Jacopo Gennaro Esposito
 */
public interface OrdineObserver {
    void onOrdineStatoChanged(Ordine ordine, Ristorante ristorante);
}
