package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Ristorante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Interfaccia che rappresenta il DAO di un Ristorante */
public interface RistoranteDAO extends JpaRepository<Ristorante, Integer> {

    @Query(value = "SELECT r.id_ristorante, r.Nome, r.Via, r.N_Civico, r.Cap, r.Provincia, " +
            "r.Telefono FROM Ristorante r JOIN Possiede p on p.id_ristorante = " +
            "r.id_ristorante where p.id = ?", nativeQuery = true)
    Optional<Set<Ristorante>> findRistoranteByOwners(Integer idRistoratore);

    @Query(value = "SELECT r.id_ristorante, r.Nome, r.Via, r.N_Civico, r.Cap, r.Provincia, " +
            "r.Telefono FROM Ristorante r JOIN Possiede p on p.id_ristorante = " +
            "r.id_ristorante where r.id_ristorante = ? and p.id = ?", nativeQuery = true)
    Optional<Ristorante> findByIdAndAndOwnersID(Integer idRistorante, Integer idRistoratore);

    @Query(value = "SELECT * FROM ristorante r\n" +
            "WHERE\n" +
            "    (MATCH(Nome) AGAINST(:Nome IN BOOLEAN MODE) OR :Nome IS NULL)\n" +
            "    AND (r.Via = COALESCE(:Via, r.Via))\n" +
            "    AND (r.N_Civico = COALESCE(:NCivico, r.N_Civico))\n" +
            "    AND (r.Provincia = COALESCE(:Provincia, r.Provincia))\n" +
            "    AND (r.Cap = COALESCE(:Cap, r.Cap));", nativeQuery = true)
    Optional<Set<Ristorante>> findRistoranteByNomeAndVia(String Nome, String Via, String NCivico, String Provincia, Integer Cap);
}
