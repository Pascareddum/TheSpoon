package it.unisa.thespoon.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Jacopo Gennaro Esposito
 *
 * Bean del BCryptPasswordEncoder usato dai servizi di autenticazione
 * */
@Configuration
@NoArgsConstructor
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
