package it.unisa.thespoon.login.control;

import it.unisa.thespoon.exceptionhandler.RestExceptionHandler;
import it.unisa.thespoon.filters.JwtAuthenticationFilter;
import it.unisa.thespoon.jwt.service.JwtService;
import it.unisa.thespoon.login.service.LoginServiceImpl;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * La classe si occupa di testare gli enpoint delle API del
 * sottosistema di login
 * */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private RistoratoreDAO ristoratoreDAO;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;
    private LoginController underTest;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.underTest = new LoginController(new LoginServiceImpl(ristoratoreDAO,
            passwordEncoder, userService, jwtService, authenticationManager));
        this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).setControllerAdvice(RestExceptionHandler.class).build();
    }

    @AfterEach
    void tearDown() {
        ristoratoreDAO.deleteAll();
    }

    /**
     * Testa l'endpoint di login effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void login() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "ShenYue98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        ristoratoreDAO.save(ristoratore);

        String jsonString = "{" +
                "\"email\": \"shen@yue.it\"," +
                "\"password\": \"ShenYue98\"" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di registrazione effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void signUp() throws Exception {
        //Given
        String jsonString = "{" +
                "\"email\": \"shen@yue.it\"," +
                "\"password\": \"ShenYue98\"," +
                "\"rePassword\": \"ShenYue98\"," +
                "\"nome\": \"Yue\"," +
                "\"cognome\": \"Shen\"," +
                "\"telefono\": \"3510857328\"," +
                "\"data_Nascita\": \"1998-10-08\"" + "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di login effettuando una richiesta con
     * password non corrispondente a quella salvata nel DB
     * */
    @Test
    void loginFailed() throws Exception {

        String jsonString = "{" +
                "\"email\": \"shen@yue.it\"," +
                "\"password\": \"ShenYue\"" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_UNAUTHORIZED, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di registrazione effettuando una richiesta con
     * parametri non validi
     * */
    @Test
    void signUPFailed() throws Exception {
        //Given
        String jsonString = "{" +
                "\"email\": \"shen@yue.it\"," +
                "\"password\": \"ShenYue\"," +
                "\"rePassword\": \"ShenYue98\"," +
                "\"nome\": \"Yue\"," +
                "\"cognome\": \"Shen\"," +
                "\"telefono\": \"3510857328\"," +
                "\"data_Nascita\": \"1998-10-08\"" + "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_BAD_REQUEST, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di registrazione effettuando una richiesta
     * inserendo una mail già presente nel DB
     * */
    @Test
    void signUPFailedUserAlreadyExists() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "ShenYue98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        ristoratoreDAO.save(ristoratore);

        String jsonString = "{" +
                "\"email\": \"shen@yue.it\"," +
                "\"password\": \"ShenYue98\"," +
                "\"rePassword\": \"ShenYue98\"," +
                "\"nome\": \"Yue\"," +
                "\"cognome\": \"Shen\"," +
                "\"telefono\": \"3510857328\"," +
                "\"data_Nascita\": \"1998-10-08\"" + "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_CONFLICT, result.getResponse().getStatus());
    }
}