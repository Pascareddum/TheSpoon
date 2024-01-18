package it.unisa.thespoon.login.service;

import it.unisa.thespoon.model.request.SignupRequest;
import it.unisa.thespoon.model.response.JwtAuthenticationResponse;
import it.unisa.thespoon.model.request.LoginRequest;

/**
Interfaccia per i metodi del sottosistema di login
 @author Jacopo Gennaro Esposito
 **/
public interface LoginService {

    /**
     * Firma del metodo per il login
     *
     * @param loginRequest Oggetto che rappresenta una richiesta di login
     * @return JwtAuthenticationResponse Token di autenticazione
     **/
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    /**
     * Firma del metodo per la registrazione
     *
     * @param registerRequest Oggetto che rappresenta una richiesta di registrazione
     * @return JwtAuthenticationResponse Token di autenticazione
     **/
    JwtAuthenticationResponse signUP(SignupRequest registerRequest);
}
