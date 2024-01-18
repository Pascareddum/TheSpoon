package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test del DAO DashboardPersonale,
 * in particolare verifica la correttezza della query custom utilizzata
 * per recuperare i dettagli di un account in base all'indirizzo email.
 * */
@SpringBootTest
class DashboardPersonaleDAOTest {

    @Autowired
    private DashboardPersonaleDAO underTest;
    String email = "jaco@jaco.it";

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    /**
     * Verifica la correttezza del valore tornato in caso di mail presente nel DB
     * */
    @Test
    void findDetailsByEmail() {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "PasswordPassword", "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        underTest.save(ristoratore);

        //When
        Optional<Ristoratore.RistoratoreDataDisplay> result = underTest.findDetailsByEmail(email);

        //Then
        assertTrue(result.isPresent());
    }

    /**
     * Verifica la correttezza del valore tornato in caso di mail presente nel DB
     * */
    @Test
    void findAllDetailsByEmail() {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "PasswordPassword", "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        underTest.save(ristoratore);

        //When
        Optional<Ristoratore> result = underTest.findAllDetailsByEmail(email);

        //Then
        assertTrue(result.isPresent());
    }

    /**
     * Verifica la correttezza del valore tornato in caso di mail non presente nel DB
     * */
    @Test
    void NotFoundDetailsByEmail() {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "PasswordPassword", "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        underTest.save(ristoratore);

        //When
        Optional<Ristoratore.RistoratoreDataDisplay> result = underTest.findDetailsByEmail("kim@dami.it");

        //Then
        assertFalse(result.isPresent());
    }

    /**
     * Verifica la correttezza del valore tornato in caso di mail non presente nel DB
     * */
    @Test
    void NotFoundAllDetailsByEmail() {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "PasswordPassword", "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        underTest.save(ristoratore);

        //When
        Optional<Ristoratore> result = underTest.findAllDetailsByEmail("kim@dami.it");

        //Then
        assertFalse(result.isPresent());
    }
}