package it.unisa.thespoon.ordini.service;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.ordini.OrdineObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe che espone i metodi del servizio Observer,
 * questo servizio gestisce gli Observer, permettendo alle classi
 * entità che dovrebbero farne uso di concentrarsi esclusivamente s
 * sulla rappresentazione dei dati
 *
 * @author Jacopo Gennaro Esposito
 * */
@Service
public class OrdineObserverServiceImpl implements OrdineObserverService {

    private Map<Class<?>, List<OrdineObserver>> observersMap = new HashMap<>();

    /**
     * Metodo che permette di aggiungere un observer per una data classe
     * @param entityType Classe da osservare
     * @param observer L'oggetto ordine observer da aggiungere
     * */
    @Override
    public void addObserver(Class<?> entityType, OrdineObserver observer) {
        observersMap.computeIfAbsent(entityType, k -> new ArrayList<>()).add(observer);
    }

    /**
     * Metodo che permette di rimuovere un observer per una data classe
     * @param entityType Classe da osservare
     * @param observer L'oggetto ordine observer da rimuovere
     * */
    @Override
    public void removeObserver(Class<?> entityType, OrdineObserver observer) {
        observersMap.computeIfPresent(entityType, (k, observers) -> {
            observers.remove(observer);
            return observers.isEmpty() ? null : observers;
        });
    }

    /**
     * Metodo che notifica gli Observer per la classe ordine
     * @param ordine Dettagli ordine
     * @param ristorante Dettagli ristorante
     * */
    @Override
    public void notifyObservers(Ordine ordine, Ristorante ristorante) {
        List<OrdineObserver> observers = observersMap.get(ordine.getClass());
        if (observers != null) {
            observers.forEach(observer -> observer.onOrdineStatoChanged(ordine, ristorante));
        }
    }
}