package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
public class InsertOrdineRequest {

    @NotNull
    List<Integer> ProductsIDs;

    @Size(max = 2)
    String NumeroTavolo;

    @NotNull
    Integer ChatID;

    @NotNull
    Integer IdRistorante;

    @NotNull
    Byte Tipologia;

    public InsertOrdineRequest(List<Integer> productsIDs, String numeroTavolo, Integer chatID, Integer idRistorante, Byte tipologia) {
        ProductsIDs = productsIDs;
        NumeroTavolo = numeroTavolo;
        ChatID = chatID;
        IdRistorante = idRistorante;
        Tipologia = tipologia;
    }

    public List<Integer> getProductsIDs() {
        return ProductsIDs;
    }

    public String getNumeroTavolo() {
        return NumeroTavolo;
    }

    public Integer getChatID() {
        return ChatID;
    }

    public Integer getIdRistorante() {
        return IdRistorante;
    }

    public Byte getTipologia() {
        return Tipologia;
    }
}
