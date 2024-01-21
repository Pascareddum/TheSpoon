package it.unisa.thespoon.model.response;


import java.sql.Time;
import java.time.LocalDate;

/**
 * Classe che rappresenta una risposta prenotazioniInfo
 * da TheSpoon
 * @author Jacopo Gennaro Esposito
 * */
public interface PrenotazioneInfo {


    Integer getIdPrenotazione();
    LocalDate getData();
    Time getOra();
    Integer getNrPersone();
    String getEmail();
    String getCellulare();
    Byte getStatoPrenotazione();
}
