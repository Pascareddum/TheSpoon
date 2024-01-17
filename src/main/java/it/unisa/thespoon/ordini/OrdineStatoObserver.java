package it.unisa.thespoon.ordini;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.notifiche.service.TelegramAdapter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jacopo Gennaro Esposito
 * OrdineObserver che costituisce il Subscriber, dichiara il metodo
 * onOrdineStatoChanged che permette di passare i dettagli dell'evento (l'ordine)
 * da notificare.
 */
@RequiredArgsConstructor
public class OrdineStatoObserver implements OrdineObserver{
    private final TelegramAdapter telegramAdapter;
    /**
     * Metodo che notifica chiamando le API di Telegram mediante l'adapter
     * @param ordine Dettagli ordine da notificare
     * @param ristorante Dettagli ristorante
     */
    @Override
    public void onOrdineStatoChanged(Ordine ordine, Ristorante ristorante) {
        if(ordine.getNr_Tavolo()==null)
            telegramAdapter.inviaMessaggioNotifica(ordine.getChatId(), "Ordine Numero:" + ordine.getIdordine() + " Prezzo: "
                + ordine.getTotale() + "                             Puoi ritirare il tuo ordine presso: " + ristorante.getNome());
        else
            telegramAdapter.inviaMessaggioNotifica(ordine.getChatId(), "Ordine Numero:" + ordine.getIdordine() + " Prezzo: "
                    + ordine.getTotale() + "                             Il tuo ordine da: " + ristorante.getNome() +" sta arrivando al tuo tavolo: " + ordine.getNr_Tavolo());
    }
}
