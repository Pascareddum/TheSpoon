package it.unisa.thespoon.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface ProdottoOrdineInfo {
    Integer getId();
    String getNome();
    String getDescrizione();
    Float getPrezzo();
    Integer getQuantita();
}