package it.unisa.thespoon.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di modifica dati personali indirizzata al sottosistema Dashboard Personale
 * */
public class UpdateRistoratoreRequest {

    @Email
    private String Email;
    @Size(min = 2, message = "Il nome deve avere una lunghezza compresa fra 2 e 30")
    @Size(max = 30, message = "Il nome deve avere una lunghezza compresa fra 2 e 30")
    private String Nome;
    @Size(min = 2, message = "Il cognome deve avere una lunghezza compresa fra 2 e 30")
    @Size(max = 30, message = "Il cognome deve avere una lunghezza compresa fra 2 e 30")
    private String Cognome;
    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[24-90]|36[680]|33[13-90]|32[890]|35[013]|37[0139]|39[23])\\d{6,7}$", message = "Numero di telefono non valido, inserire un numero italiano")
    private String Telefono;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate Data_Nascita;

    public UpdateRistoratoreRequest(String email, String nome, String cognome, String telefono, LocalDate data_Nascita) {
        Email = email;
        Nome = nome;
        Cognome = cognome;
        Telefono = telefono;
        Data_Nascita = data_Nascita;
    }

    public String getEmail() {
        return Email;
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
