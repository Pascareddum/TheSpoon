package it.unisa.thespoon.model.dao;


import it.unisa.thespoon.model.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO di un Prodotto */
public interface ProdottoDAO extends JpaRepository<Prodotto, Integer> {

    @Query(value = "SELECT * FROM Prodotto p join menuprodotto " +
            "m on m.IdProdotto = p.IdProdotto " +
            "where m.IdMenu = ?", nativeQuery = true)
    Optional<Set<Prodotto>> findProdottiInMenu(Integer idMenu);

    @Query(value = "SELECT * FROM prodotto p where id_prodotto = ?", nativeQuery = true)
    Optional<Prodotto> findSingleProdottoById(Integer idProdotto);
}
