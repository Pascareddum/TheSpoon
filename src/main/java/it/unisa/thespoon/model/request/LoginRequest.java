package it.unisa.thespoon.model.request;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di login indirizzata al sottosistema di login
 * */
public class LoginRequest {

    private String Email;
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
