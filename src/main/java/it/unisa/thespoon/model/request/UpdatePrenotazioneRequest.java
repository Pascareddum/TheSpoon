package it.unisa.thespoon.model.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe che rappresenta una richiesta di modifica prenotazione
 * indirizzata al sottosistema prenotazioni
 * @author Jacopo Gennaro Esposito
 * */
public class UpdatePrenotazioneRequest {

    private List<String> tableIDs;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate Data;

    private Time Ora;

    @Positive
    private Integer Nr_Persone;

    @NotNull
    @Positive
    private Integer idPrenotazione;

    @NotNull
    private String password;

    public UpdatePrenotazioneRequest(List<String> tableIDs, LocalDate data, Time ora, Integer nr_Persone, Integer idPrenotazione, String password) {
        this.tableIDs = tableIDs;
        Data = data;
        Ora = ora;
        Nr_Persone = nr_Persone;
        this.idPrenotazione = idPrenotazione;
        this.password = password;
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

    public Integer getIdPrenotazione() {
        return idPrenotazione;
    }

    public String getPassword() {
        return password;
    }
}
