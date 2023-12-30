package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di modifica password indirizzata al sottosistema Dashboard Personale
 * */
public class UpdatePasswordRequest {
    @NotNull
    @Size(min = 8, message = "La password deve avere una lunghezza di almeno 8 caratteri")
    private String Password;
    @NotNull
    private String rePassword;

    public UpdatePasswordRequest(String password, String rePassword) {
        Password = password;
        this.rePassword = rePassword;
    }

    public String getPassword() {
        return Password;
    }

    public String getRePassword() {
        return rePassword;
    }
}
