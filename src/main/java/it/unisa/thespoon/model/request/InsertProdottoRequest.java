package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Classe che rappresenta una richiesta di inserimento prodotto
 * indirizzata al sottosistema prodotto
 * @author Jacopo Gennaro Esposito
 * */
public class InsertProdottoRequest {

    @NotNull
    @Size(max = 30, message = "Il nome del prodotto può contenere massimo 30 caratteri")
    String Nome;

    @NotNull
    @Size(max = 2000, message = "La descrizione può avere una lunghezza massima di 2000 caratteri")
    String Descrizione;

    @NotNull
    @PositiveOrZero
    BigDecimal prezzo;

    public InsertProdottoRequest(String nome, String descrizione, BigDecimal prezzo) {
        Nome = nome;
        Descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }
}
