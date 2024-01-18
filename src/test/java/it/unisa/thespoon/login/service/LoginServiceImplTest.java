package it.unisa.thespoon.login.service;

import it.unisa.thespoon.exceptionhandler.InvalidAuthCredentials;
import it.unisa.thespoon.exceptionhandler.UserAlreadyExistsException;
import it.unisa.thespoon.jwt.service.JwtService;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import it.unisa.thespoon.model.request.LoginRequest;
import it.unisa.thespoon.model.request.SignupRequest;
import it.unisa.thespoon.model.response.JwtAuthenticationResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test per la classe LoginService,
 * in particolare verranno testate le funzionalità di login e registrazione.
 * */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Autowired
    private RistoratoreDAO ristoratoreDAO;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private LoginServiceImpl underTest;

    @BeforeEach
    void setUp(){
        this.underTest = new LoginServiceImpl(ristoratoreDAO, passwordEncoder, userService, jwtService, authenticationManager);
    }

    @AfterEach
    void tearDown(){
        ristoratoreDAO.deleteAll();
    }

    /**
     * Testa la funzione di login inserendo parametri validi e
     * corrispondendti ad un account registrato.
     * */
    @Test
    void login() {
        //given
        String email = "shen@yue.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", email, "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        //When
        JwtAuthenticationResponse jwtAuthenticationResponse = underTest.login(new LoginRequest(email, password));

        assertNotNull(jwtAuthenticationResponse);
    }

    /**
     * Testa la funzione di registrazione inserendo parametri
     * validi nella richiesta di registrazione
     * */
    @Test
    void signUP() {

        JwtAuthenticationResponse jwtAuthenticationResponse = underTest.signUP(new SignupRequest("jaco.espo@hotmail.it", "JacopoEsposito",
                "JacopoEsposito", "Jacopo", "Esposito", "3510857328", LocalDate.now()));

        assertNotNull(jwtAuthenticationResponse);
    }

    /**
     * Testa la funzione di login inserendo una mail non assocciata a nessun
     * account registrato
     * */
    @Test
    void loginFailed(){
        //given
        String email = "shen@yue.it";
        String password = "ShenYue";

        Ristoratore ristoratore = new Ristoratore(1, password, "Jacopo"
                , "Espsosito", "kim@dami.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        //When
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = underTest.login(new LoginRequest(email, password));
        }
        catch (Exception e){
            assertEquals(e.getLocalizedMessage(), "Invalid email or password.");
        }

    }

    /**
     * Testa la funzione di registrazione inserendo dei parametri non validi.
     * */
    @Test
    void signUPFailed(){
        JwtAuthenticationResponse jwtAuthenticationResponse = underTest.signUP(new SignupRequest("jaco.espo@hotmail", "JacopoEsposito",
                "JacopoEsposito", "Jacopo", "Esposito", "3510857328", LocalDate.now()));

        assertNull(jwtAuthenticationResponse.getToken());

    }

    /**
     * Testa la funzione di registrazione inserendo una mail già associata ad
     * un account di The Spoon
     * */
    @Test
    void signUPFailedAccAlreadyExists(){
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "Password", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore r = userService.save(ristoratore);

        //When
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = underTest.signUP(new SignupRequest("shen@yue.it", "JacopoEsposito",
                    "JacopoEsposito", "Jacopo", "Esposito", "3510857328", LocalDate.now()));
        }catch(Exception ex){
            //Then
            assertEquals(ex.getLocalizedMessage(), "Account already registered to TheSpoon");
        }

    }
}