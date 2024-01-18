package it.unisa.thespoon.model.dao;

import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test del DAO Ristoratore,
 * in particolare verifica la correttezza della query custom utilizzata
 * per recuperare un account in base all'indirizzo email.
 * */
@SpringBootTest
class RistoratoreDAOTest {
    @Autowired
    private RistoratoreDAO underTest;
    String email = "jaco@jaco.it";

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    /**
     * Verifica la correttezza del valore tornato in caso di email presente nel DB
     * */
    @Test
    void findByEmail() {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "PasswordPassword", "Jacopo"
        , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        underTest.save(ristoratore);

        //When
        Optional<Ristoratore> result = underTest.findByEmail(email);

        //Then
        assertTrue(result.isPresent());
    }

    /**
     * Verifica la correttezza del valore tornato in caso di email non presente nel DB
     * */
    @Test
    void NotFoundByEmail() {

        //When
        Optional<Ristoratore> result = underTest.findByEmail(email);

        //Then
        assertTrue(result.isEmpty());
    }
}