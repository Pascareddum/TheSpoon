package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test del DAO Ristoratore.
 * */
@SpringBootTest
class RistoranteDAOTest {

    @Autowired
    private RistoranteDAO underTest;

    @Autowired
    private UserService userService;

    private Set<Ristoratore> RisSet;

    private Set<Ristorante> Ristoranti;


    @BeforeEach
    void setUp() {
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    /** Verifica la correttezza del valore tornato per la query di
     * ricerca ristorante mediante ID Ristoratore
    */
     @Test
    void findRistoranteByOwners() {
        Ristorante ristorante = new Ristorante(10, "Pizzeria Tuino", "12", 80056, "Via E Caianiello", "SA", "003930090004356", null);
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);

        underTest.save(ristorante);

        ristoratore.getRistoranti().add(ristorante);
        userService.save(ristoratore);

        //When
        Optional<Set<Ristorante>> ristoranti = underTest.findRistoranteByOwners(ristoratore.getId());

        //Then
        assertTrue(ristoranti.isPresent());
        assertEquals(ristorante.getNome(), ristoranti.get().iterator().next().getNome());
    }

    /**
     * Verifica la correttezza del valore tornato per la query di ricerca ristorante mediante ID e ID Ristoratore
     * */
    @Test
    void findByIdAndAndOwnersID() {
        Ristorante ristorante = new Ristorante(10, "Pizzeria Tuino", "12", 80056, "Via E Caianiello", "SA", "003930090004356", null);
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);

        underTest.save(ristorante);

        ristoratore.getRistoranti().add(ristorante);
        userService.save(ristoratore);

        //When
        Optional<Ristorante> ristoranti = underTest.findByIdAndAndOwnersID(ristorante.getId(), ristoratore.getId());

        //Then
        assertEquals(ristorante.getNome(), ristoranti.get().getNome());
    }
}