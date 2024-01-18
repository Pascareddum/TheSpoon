package it.unisa.thespoon.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * Classe che rappresenta una risposta prodottoinfo
 * da TheSpoon
 * @author Jacopo Gennaro Esposito
 * */
public interface ProdottoOrdineInfo {
    Integer getId();
    String getNome();
    String getDescrizione();
    Float getPrezzo();
    Integer getQuantita();
}