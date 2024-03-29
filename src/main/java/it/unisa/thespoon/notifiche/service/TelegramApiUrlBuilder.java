package it.unisa.thespoon.notifiche.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * Classe che rappresenta un UrlBuilder per la API SendMessage di Telegram
 * @author Jacopo Gennaro Esposito
 * */
@Service
public class TelegramApiUrlBuilder {
    @Value("${TELEGRAMTOKEN}")
    String telegramToken;

    /**
     * Metodo che compone l'URL per effettuare la richiesta sendMessage alle API di Telegram
     *
     * @param chatID Identificativo univoco della chat telegram
     * @param text Testo del messaggio
     * @return String Stringa contenente l'URI dell'api da contattare con il messaggio e il chatID
     */
    public String buildSendTelegramMessage(Integer chatID, String text){
        String newString = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String finalString = newString.replace(' ', '+');

        String baseUrl = "https://api.telegram.org/bot" + telegramToken + "/sendMessage";
        String encodedString = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("chat_id", chatID)
                .queryParam("text", finalString)
                .build(true)
                .toUriString();
        System.out.println(encodedString);

        return encodedString;
    }
}
