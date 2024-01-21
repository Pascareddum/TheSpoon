package it.unisa.thespoon.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;


/**
 * Classe che rappresenta una richiesta di inserimento prenotazione
 * indirizzata al sottosistema prenotazioni
 * @author Jacopo Gennaro Esposito
 * */
public class InsertPrenotazioneRequest {

    @NotNull
    private List<String> tableIDs;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate Data;

    @NotNull
    private Time Ora;

    @NotNull
    @Positive
    private Integer Nr_Persone;

    @Email
    private String Email;

    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[24-90]|36[680]|33[13-90]|32[890]|35[013]|37[0139]|39[23])\\d{6,7}$", message = "Numero di telefono non valido, inserire un numero italiano")
    private String Telefono;

    @NotNull
    @Positive
    private Integer idRistorante;

    @NotNull
    private Integer ChatID;

    public InsertPrenotazioneRequest(List<String> tableIDs, LocalDate data, Time ora, Integer nr_Persone, String email, String telefono, Integer idRistorante, Integer chatID) {
        this.tableIDs = tableIDs;
        Data = data;
        Ora = ora;
        Nr_Persone = nr_Persone;
        Email = email;
        Telefono = telefono;
        this.idRistorante = idRistorante;
        this.ChatID = chatID;
    }

    public List<String> getTableIDs() {
        return tableIDs;
    }

    public LocalDate getData() {
        return Data;
    }

    public Time getOra() {
        return Ora;
    }

    public Integer getNr_Persone() {
        return Nr_Persone;
    }

    public String getEmail() {
        return Email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public Integer getIdRistorante() {
        return idRistorante;
    }

    public Integer getChatID() {
        return ChatID;
    }
}
