package it.unisa.thespoon.notifiche.service;

import java.net.URISyntaxException;

public interface TelegramAdapter {

    void inviaMessaggioNotifica(Integer chatID, String text);
}
