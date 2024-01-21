package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.model.entity.Tavolo;
import it.unisa.thespoon.model.response.PrenotazioneInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO di una Prenotazione */
public interface PrenotazioneDAO extends JpaRepository<Prenotazione, Integer> {
    Optional<Prenotazione> findPrenotazioneByDataAndOraAndTavoli(LocalDate data, Time ora, Tavolo tavoli);

    Optional<Prenotazione> findPrenotazioneByDataAndOraAndTavoliAndIdIsNot(LocalDate data, Time ora, Tavolo tavoli, Integer id);

    @Query("SELECT DISTINCT p.Id as idPrenotazione, p.data as data, p.ora as ora, " +
            "p.Nr_Persone as nrPersone, p.Email as email, p.Cellulare as cellulare, p.Stato as statoPrenotazione " +
            "FROM Prenotazione p WHERE p.ownerPrenotazione.Id = :idRistorante")
    List<PrenotazioneInfo> findPrenotazioneTavoloByIdRistorante(Integer idRistorante);

    @Query("SELECT t FROM Prenotazione p join p.tavoli t where p.Id = :idPrenotazione")
    List<Tavolo> findTavoliByIdPrenotazione(Integer idPrenotazione);
}
