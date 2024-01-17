package it.unisa.thespoon.notifiche.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramApiUrlBuilder {
    @Value("${TELEGRAMTOKEN}")
    String telegramToken;

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
