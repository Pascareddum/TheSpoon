package it.unisa.thespoon.config;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.notifiche.service.TelegramAdapter;
import it.unisa.thespoon.ordini.OrdineStatoObserver;
import it.unisa.thespoon.ordini.service.OrdineObserverService;
import it.unisa.thespoon.prenotazioni.PrenotazioniObserver;
import it.unisa.thespoon.prenotazioni.PrenotazioniStatoObserver;
import it.unisa.thespoon.prenotazioni.service.PrenotazioneObserverService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class per la gestione centralizzata degli
 * observer.
 * Configura e registra gli observer necessari al momento dell'inizializzazione dell'applicazione.
 * @author Jacopo Gennaro Esposito
 */
@Configuration
public class ObserverConfig {

    @Autowired
    private OrdineObserverService ordineObserverService;

    @Autowired
    private PrenotazioneObserverService prenotazioneObserverService;

    @Autowired
    private TelegramAdapter telegramAdapter;

    /**
     * Metodo chiamato al momento dell'inizializzazione dell'applicazione.
     * Registra gli osservatori necessari, come ad esempio l'osservatore dello stato dell'ordine.
     */
    @PostConstruct
    public void registerObservers() {
        OrdineStatoObserver osservatore = new OrdineStatoObserver(telegramAdapter);
        PrenotazioniStatoObserver osservatorePrenotazioni = new PrenotazioniStatoObserver(telegramAdapter);
        ordineObserverService.addObserver(Ordine.class, osservatore);
        prenotazioneObserverService.addObserver(Prenotazione.class, osservatorePrenotazioni);
    }
}
