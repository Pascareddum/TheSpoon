package it.unisa.thespoon.prenotazioni;

import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.notifiche.service.TelegramAdapter;
import lombok.RequiredArgsConstructor;

/**
 * OrdineObserver che costituisce il Subscriber, dichiara il metodo
 * onPrenotazioneStatoChanged che permette di passare i dettagli dell'evento (la prenotazione)
 * da notificare.
 * @author Jacopo Gennaro Esposito
 */
@RequiredArgsConstructor

public class PrenotazioniStatoObserver implements PrenotazioniObserver{
    private final TelegramAdapter telegramAdapter;

    /**
     * Metodo che notifica chiamando le API di Telegram mediante l'adapter
     * @param prenotazione Dettagli prenotazione da notificare
     * @param ristorante Dettagli ristorante
     */
    @Override
    public void onPrenotazioneStatoChanged(Prenotazione prenotazione, Ristorante ristorante) {
        if(prenotazione.getStato() == 1){
            telegramAdapter.inviaMessaggioNotifica(prenotazione.getChatId(), "Hai effettuato una prenotazione presso: " + ristorante.getNome() + "                                   " +
                    " Data Prenotazione: " + prenotazione.getData() + "  Orario: " + prenotazione.getOra() + "                                                               Id Prenotazione: " + prenotazione.getId() + " Password prenotazione: " + prenotazione.getPasswordprenotazione());
        } else if(prenotazione.getStato() == 2) {
            telegramAdapter.inviaMessaggioNotifica(prenotazione.getChatId(), "E' stata confermata la tua prenotazione presso: " + ristorante.getNome() + "                                   " +
                    " In Data: " + prenotazione.getData() + "  Orario: " + prenotazione.getOra());

        } else if (prenotazione.getStato() == 3) {
            telegramAdapter.inviaMessaggioNotifica(prenotazione.getChatId(), "Hai modificato una prenotazione presso: " + ristorante.getNome() + "                                   " +
                    " In Data: " + prenotazione.getData() + "  Orario: " + prenotazione.getOra() + "                                                               Id Prenotazione: " + prenotazione.getId() + " Password prenotazione: " + prenotazione.getPasswordprenotazione());
        }
    }
}
