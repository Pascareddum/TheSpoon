package it.unisa.thespoon.notifiche.service;

/**
 * Adapter per le API di Telegram
 * @author Jacopo Gennaro Esposito
 */
public interface TelegramAdapter {

    void inviaMessaggioNotifica(Integer chatID, String text);
}
