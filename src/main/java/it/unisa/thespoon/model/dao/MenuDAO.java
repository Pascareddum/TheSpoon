package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Menu;
import it.unisa.thespoon.model.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO di un Menu */
public interface MenuDAO extends JpaRepository<Menu, Integer> {

    Optional<Set<Menu>> findMenusByRistoranteId(Integer IdRistorante);


}
