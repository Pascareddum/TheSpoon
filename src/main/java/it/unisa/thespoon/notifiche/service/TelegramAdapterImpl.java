package it.unisa.thespoon.notifiche.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TelegramAdapterImpl implements TelegramAdapter{
    private final TelegramApiUrlBuilder telegramApiUrlBuilder;
    private final RestTemplate restTemplate;

    public TelegramAdapterImpl(TelegramApiUrlBuilder telegramApiUrlBuilder, RestTemplate restTemplate) {
        this.telegramApiUrlBuilder = telegramApiUrlBuilder;
        this.restTemplate = restTemplate;
    }

    /**
     * Metodo per inviare un messaggio attravverso le API di Telegram
     * @param chatID Identificativo univoco della chat telegram
     * @param text Testo del messaggio
     */
    @Override
    public void inviaMessaggioNotifica(Integer chatID, String text){
        String telegramAPIUri = telegramApiUrlBuilder.buildSendTelegramMessage(chatID, text);

        URI uri = null;
        try {
            uri = new URI(telegramAPIUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        restTemplate.postForObject(uri, null, String.class);
    }
}
