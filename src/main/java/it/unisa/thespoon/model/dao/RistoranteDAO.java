package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Ristorante;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO di un Ristorante */
public interface RistoranteDAO extends JpaRepository<Ristorante, Integer> {

}
