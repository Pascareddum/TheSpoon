package it.unisa.thespoon.notifiche.service;

public interface TelegramAdapter {

    void inviaMessaggioNotifica(Integer chatID, String text);
}
