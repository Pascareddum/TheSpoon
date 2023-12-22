package it.unisa.thespoon.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di registrazione indirizzata al sottosistema di login
 * */
public class SignupRequest {
    @NotNull
    @jakarta.validation.constraints.Email
    private String Email;
    @NotNull
    private String Password;
    @NotNull
    private String rePassword;
    @NotNull
    private String Nome;
    @NotNull
    private String Cognome;
    @NotNull
    private String Telefono;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate Data_Nascita;

    public SignupRequest(String email, String password, String rePassword, String nome, String cognome, String telefono, LocalDate data_Nascita) {
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

    public LocalDate getData_Nascita() {
        return Data_Nascita;
    }
}
