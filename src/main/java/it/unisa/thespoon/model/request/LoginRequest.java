package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.NotNull;

/**
 * Classe che rappresenta una richiesta di login indirizzata al sottosistema di login
 * @author Jacopo Gennaro Esposito
 * */
public class LoginRequest {

    @NotNull
    private String Email;
    @NotNull
    private String Password;

    public LoginRequest(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
