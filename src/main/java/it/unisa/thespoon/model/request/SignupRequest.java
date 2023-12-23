package it.unisa.thespoon.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, message = "La password deve avere una lunghezza di almeno 8 caratteri")
    private String Password;
    @NotNull
    private String rePassword;
    @NotNull
    @Size(min = 2, message = "Il nome deve avere una lunghezza compresa fra 2 e 30")
    @Size(max = 30, message = "Il nome deve avere una lunghezza compresa fra 2 e 30")
    private String Nome;
    @NotNull
    @Size(min = 2, message = "Il cognome deve avere una lunghezza compresa fra 2 e 30")
    @Size(max = 30, message = "Il cognome deve avere una lunghezza compresa fra 2 e 30")
    private String Cognome;
    @NotNull
    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[24-90]|36[680]|33[13-90]|32[890]|35[013]|37[0139]|39[23])\\d{6,7}$", message = "Numero di telefono non valido, inserire un numero italiano")
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
