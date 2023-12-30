package it.unisa.thespoon.dashboardpersonale.controller;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleImpl;
import it.unisa.thespoon.exceptionhandler.RestExceptionHandler;
import it.unisa.thespoon.jwt.service.JwtService;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.DashboardPersonaleDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * La classe si occupa di testare gli enpoint delle API del
 * sottosistema dashboard personale
 * */
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestExceptionHandler.class)
@ExtendWith(MockitoExtension.class)
class DashboardPersonaleControllerTest {

    @Autowired
    private DashboardPersonaleDAO dashboardPersonaleDAO;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    private DashboardPersonaleController underTest;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.underTest = new DashboardPersonaleController(new DashboardPersonaleImpl(dashboardPersonaleDAO, passwordEncoder));
    }

    @AfterEach
    void tearDown() {
        dashboardPersonaleDAO.deleteAll();
    }

    /**
     * Testa l'endpoint getRistoratoreDetails effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void getRistoratoreDetails() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        dashboardPersonaleDAO.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dashboard/ristoratoreDetails")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint updateRistoratoreDetails effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void updateRistoratoreDetails() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        dashboardPersonaleDAO.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{\n" +
                "    \"nome\": \"Yue\",\n" +
                "    \"cognome\": \"Shen\",\n" +
                "    \"telefono\": \"00393338981042\",\n" +
                "    \"data_Nascita\": \"1997-02-27\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dashboard/updateRistoratoreDetails")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
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
     * Testa l'endpoint updatePassword effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void updatePassword() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        dashboardPersonaleDAO.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{\n" +
                "    \"password\": \"KimDami1998\",\n" +
                "    \"rePassword\": \"KimDami1998\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dashboard/updatePassword")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
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
     * Testa l'endpoint getRistoratoreDetails effettuando una richiesta con
     * parametri non validi
     * */
    @Test
    void FailedGetRistoratoreDetails() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        Ristoratore utenteNotRegistered = new Ristoratore(2, "KimDami98", "Suzy"
                , "Bae", "bae@suzy.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);

        dashboardPersonaleDAO.save(ristoratore);

        var tokenString = jwtService.generateToken(utenteNotRegistered);

        System.out.println(tokenString);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dashboard/ristoratoreDetails")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_UNAUTHORIZED, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint updateRistoratoreDetails effettuando una richiesta con
     * parametri non validi
     * */
    @Test
    void FailedUpdateRistoratoreDetails() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        dashboardPersonaleDAO.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{\n" +
                "    \"nome\": \"Y\",\n" +
                "    \"cognome\": \"S\",\n" +
                "    \"telefono\": \"00393338981042\",\n" +
                "    \"data_Nascita\": \"1997-02-27\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dashboard/updateRistoratoreDetails")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
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
     * Testa l'endpoint updatePassword effettuando una richiesta con
     * parametri non validi
     * */
    @Test
    void FailedUpdatePassword() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE);
        dashboardPersonaleDAO.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{\n" +
                "    \"password\": \"KimDami1998\",\n" +
                "    \"rePassword\": \"KimDami19\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dashboard/updatePassword")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_BAD_REQUEST, result.getResponse().getStatus());
    }
}