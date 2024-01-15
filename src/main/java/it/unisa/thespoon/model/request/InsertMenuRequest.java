package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di inserimento menu
 * indirizzata al sottosistema ristorante
 * */
public class InsertMenuRequest {

    @NotNull
    @Size(max = 30, message = "Il nome del menu non può superare i trenta caratteri")
    @Size(min = 4, message = "Il nome deve possedere una lunghezza di almeno quattro caratteri")
    String Nome;

    @NotNull
    @Size(max = 30, message = "La descrizione del menu non può superare i trenta caratteri")
    @Size(min = 4, message = "La descrizione del menu deve possedere una lunghezza di almeno quattro caratteri")
    String Descrizione;

    @NotNull
    Integer idRistorante;

    public InsertMenuRequest(String nome, String descrizione, Integer idRistorante) {
        Nome = nome;
        Descrizione = descrizione;
        this.idRistorante = idRistorante;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public Integer getIdRistorante() {
        return idRistorante;
    }
}
