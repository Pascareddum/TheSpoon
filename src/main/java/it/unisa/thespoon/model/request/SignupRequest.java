package it.unisa.thespoon.model.request;

import java.util.Date;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di registrazione indirizzata al sottosistema di login
 * */
public class SignupRequest {
    private String Email;
    private String Password;
    private String rePassword;
    private String Nome;
    private String Cognome;
    private String Telefono;
    private Date Data_Nascita;

    public SignupRequest(String email, String password, String rePassword, String nome, String cognome, String telefono, Date data_Nascita) {
        Email = email;
        Password = password;
        this.rePassword = rePassword;
        Nome = nome;
        Cognome = cognome;
        Telefono = telefono;
        Data_Nascita = data_Nascita;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public String getNome() {
        return Nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public String getTelefono() {
        return Telefono;
    }

    public Date getData_Nascita() {
        return Data_Nascita;
    }
}
