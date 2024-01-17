package it.unisa.thespoon.ordini.service;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.ordini.OrdineObserver;

/**
 * Interfaccia che espone i metodi del servizio Observer,
 * questo servizio gestisce gli Observer, permettendo alle classi
 * entità che dovrebbero farne uso di concentrarsi esclusivamente s
 * sulla rappresentazione dei dati
 *
 * @author Jacopo Gennaro Esposito
 * */
public interface OrdineObserverService {

    /**
     * Metodo che permette di aggiungere un observer per una data classe
     * @param entityType Classe da osservare
     * @param observer L'oggetto ordine observer da aggiungere
     * */
    void addObserver(Class<?> entityType, OrdineObserver observer);

    /**
     * Metodo che permette di rimuovere un observer per una data classe
     * @param entityType Classe da osservare
     * @param observer L'oggetto ordine observer da rimuovere
     * */
    void removeObserver(Class<?> entityType, OrdineObserver observer);

    /**
     * Metodo che notifica gli Observer per la classe ordine
     * @param ordine Dettagli ordine
     * @param ristorante Dettagli ristorante
     * */
    void notifyObservers(Ordine ordine, Ristorante ristorante);
}
