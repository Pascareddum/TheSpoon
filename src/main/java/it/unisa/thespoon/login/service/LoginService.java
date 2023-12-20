package it.unisa.thespoon.login.service;

import it.unisa.thespoon.model.entity.Ristoratore;

/*
@author Jacopo Gennaro Esposito
Interfaccia per i metodi del sottosistema di login
 */
public interface LoginService {

    /* Firma del metodo per il login
    @param email dell'utente
    */
    Ristoratore login(String Email, String Password);
}
