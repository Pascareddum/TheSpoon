package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Ristoratore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO della DashboardPersonale
 */
public interface DashboardPersonaleDAO extends JpaRepository<Ristoratore, Integer> {

    /**
     * Query per recuperare un ristoratore data una mail
     */
    @Query(value = "SELECT Nome, Cognome, Email, Telefono, Data_Nascita FROM Ristoratore r where r.Email=?", nativeQuery = true)
    Optional<Ristoratore.RistoratoreDataDisplay> findDetailsByEmail(String Email);

    /** Query per recuperare i dettagli completi di un ristoratore data una mail */
    @Query(value = "SELECT * FROM Ristoratore r where r.Email=?", nativeQuery = true)
    Optional<Ristoratore> findAllDetailsByEmail(String Email);




}
