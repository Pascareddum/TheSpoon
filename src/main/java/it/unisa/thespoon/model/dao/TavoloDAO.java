package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Tavolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO di un Tavolo */
public interface TavoloDAO extends JpaRepository<Tavolo, Integer> {

    Optional<Set<Tavolo>> findByRistorantePropId(Integer id);

    @Query(value = "SELECT * FROM Tavolo t where t.NumeroTavolo = ? and t.id_ristorante = ?", nativeQuery = true)
    Optional<Tavolo> findByNumeroTavoloAndRistorantePropId(String numeroTavolo, Integer id);

}
