package it.unisa.thespoon.notifiche.service;

/**
 * @author Jacopo Gennaro Esposito
 *
 * Adapter per le API di Telegram
 */
public interface TelegramAdapter {

    void inviaMessaggioNotifica(Integer chatID, String text);
}
