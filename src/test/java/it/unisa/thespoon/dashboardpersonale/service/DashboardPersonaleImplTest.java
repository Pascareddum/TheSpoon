package it.unisa.thespoon.dashboardpersonale.service;

import it.unisa.thespoon.login.service.LoginServiceImpl;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.DashboardPersonaleDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import it.unisa.thespoon.model.request.UpdatePasswordRequest;
import it.unisa.thespoon.model.request.UpdateRistoratoreRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test per la classe DashboardPersonale,
 * in particolare verranno testate le funzionalità getRistoratoreDetails,
 * updateRistoratoreDetails, updatePassword.
 * */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DashboardPersonaleImplTest {

    @Autowired
    private DashboardPersonaleDAO dashboardPersonaleDAO;

    @Autowired
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private DashboardPersonaleImpl underTest;

    @BeforeEach
    void setUp(){
        this.underTest = new DashboardPersonaleImpl(dashboardPersonaleDAO, passwordEncoder);
    }

    @AfterEach
    void tearDown(){
        dashboardPersonaleDAO.deleteAll();
    }


    /**
     * Testa la funzione di getRistoratoreDetails inserendo parametri validi e
     * corrispondendti ad un account registrato.
     * */
    @Test
    void getRistoratoreDetails() {
        //given
        String email = "shen@yue.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        //When
        ResponseEntity<Ristoratore.RistoratoreDataDisplay> result = underTest.getRistoratoreDetails(email);

        //Then
        assertEquals(result.getBody().getEmail(), email);
    }

    /**
     * Testa la funzione di updateRistoratoreDetails inserendo parametri validi e
     * inoltrata ad un account registrato.
     * */
    @Test
    void updateRistoratoreDetails() {
        String email = "shen@yue.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        UpdateRistoratoreRequest updatedData = new UpdateRistoratoreRequest(null, "Dami", "Kim", "3338981042", LocalDate.now());

        //When
        ResponseEntity<HttpStatus> result = underTest.updateRistoratoreDetails(updatedData, email);

        //Then
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Testa la funzione di updatePassword inserendo parametri validi e
     * inoltrata ad un account registrato.
     * */
    @Test
    void updatePassword() {
        //Given
        String email = "shen@yue.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("KimDami", "KimDami");

        //When
        ResponseEntity<HttpStatus> result = underTest.updatePassword(updatePasswordRequest, email);

        //Then
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Testa la funzione di getRistoratoreDetails inoltrandola da
     * un account non registrato
     * */
    @Test
    void FailedGetRistoratoreDetails() {
        //given
        String email = "kimdami@love.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        //When
        try {
            ResponseEntity<Ristoratore.RistoratoreDataDisplay> result = underTest.getRistoratoreDetails(email);
        }
        catch (Exception e){
            //Then
            assertEquals(e.getLocalizedMessage(), "Account not registered at The Spoon");
        }
    }

    /**
     * Testa la funzione di updateRistoratoreDetails inserendo parametri non validi
     * */
    @Test
    void FailedUpdateRistoratoreDetails() {
        String email = "kimdami@love.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        UpdateRistoratoreRequest updatedData = new UpdateRistoratoreRequest(null, "Dami", "Kim", "3338981042", LocalDate.now());

        //When
        try {
            ResponseEntity<HttpStatus> result = underTest.updateRistoratoreDetails(updatedData, email);
        }
        catch (Exception e){
            //Then
            assertEquals(e.getLocalizedMessage(), "Account not registered at The Spoon");
        }
    }

    /**
     * Testa la funzione di updateRistoratoreDetails inserendo parametri validi
     * ma inoltrati da un account non registrato
     * */
    @Test
    void FailedUpdatePasswordAccNotFind() {
        //Given
        String email = "kimdami@love.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("KimDami", "KimDami");

        //When
        try {
            ResponseEntity<HttpStatus> result = underTest.updatePassword(updatePasswordRequest, email);
        }
        catch (Exception e){
            //Then
            assertEquals(e.getLocalizedMessage(), "Account not registered at The Spoon");
        }
    }

    /**
     * Testa la funzione di updatePassword inserendo parametri non validi validi
     * */
    @Test
    void FailedUpdatePasswordPassNotMatch() {
        //Given
        String email = "shen@yue.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("KimDami", "KimDami98");

        //When
        try {
            ResponseEntity<HttpStatus> result = underTest.updatePassword(updatePasswordRequest, email);
        }
        catch (Exception e){
            //Then
            assertEquals(e.getLocalizedMessage(), "Le password inserite non corrispondono");
        }
    }
}