package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class InsertTavoloRequest {

    @NotNull
    @Size(max = 2)
    String numeroTavolo;

    @NotNull
    Byte Stato;

    @NotNull
    @Positive
    Integer Capacita;

    @NotNull
    Integer idRistorante;

    public InsertTavoloRequest(String numeroTavolo, Byte stato, Integer capacita, Integer idRistorante) {
        this.numeroTavolo = numeroTavolo;
        Stato = stato;
        Capacita = capacita;
        this.idRistorante = idRistorante;
    }

    public String getNumeroTavolo() {
        return numeroTavolo;
    }

    public Byte getStato() {
        return Stato;
    }

    public Integer getCapacita() {
        return Capacita;
    }

    public Integer getIdRistorante() {
        return idRistorante;
    }
}
