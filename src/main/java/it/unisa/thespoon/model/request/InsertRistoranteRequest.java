package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.*;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una richiesta di inserimento ristorante
 * indirizzata al sottosistema ristorante
 * */
public class InsertRistoranteRequest {

    @NotNull
    @Size(max = 30, message = "Il nome deve avere una lunghezza massima di 30 caratteri")
    private String Nome;

    @NotNull
    @Size(max = 3, message = "Il numero civico deve avere una lunghezza massima di 3")
    private String N_Civico;

    @NotNull
    private Integer Cap;

    @NotNull
    @Size(max = 30, message = "La via deve avere una lunghezza massima di 30 caratteri")
    private String Via;

    @NotNull
    @Size(max = 2, message = "La provincia deve avere una lunghezza massima di 2 caratteri")
    private String Provincia;

    @NotNull
    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[24-90]|36[680]|33[13-90]|32[890]|35[013]|37[0139]|39[23])\\d{6,7}$", message = "Numero di telefono non valido, inserire un numero italiano")
    private String Telefono;

    public InsertRistoranteRequest(String nome, String n_Civico, Integer cap, String via, String provincia, String telefono) {
        Nome = nome;
        N_Civico = n_Civico;
        Cap = cap;
        Via = via;
        Provincia = provincia;
        Telefono = telefono;
    }

    public String getNome() {
        return Nome;
    }

    public String getN_Civico() {
        return N_Civico;
    }

    public Integer getCap() {
        return Cap;
    }

    public String getVia() {
        return Via;
    }

    public String getProvincia() {
        return Provincia;
    }

    public String getTelefono() {
        return Telefono;
    }
}
