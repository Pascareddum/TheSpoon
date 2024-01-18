package it.unisa.thespoon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Bean per la configurazione di restTemplate per effettuare chiamate REST
 * @author Jacopo Gennaro Esposito
 * */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}