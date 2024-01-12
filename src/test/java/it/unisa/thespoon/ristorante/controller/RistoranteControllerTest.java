package it.unisa.thespoon.ristorante.controller;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.exceptionhandler.RestExceptionHandler;
import it.unisa.thespoon.jwt.service.JwtService;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import it.unisa.thespoon.ristorante.service.RistoranteServiceImpl;
import jakarta.transaction.Transactional;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Jacopo Gennaro Esposito
 * La classe si occupa di testare gli enpoint delle API del
 * sottosistema ristorante
 * */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@Import(RestExceptionHandler.class)
@ExtendWith(MockitoExtension.class)
class RistoranteControllerTest {

    @Autowired
    private RistoranteController ristoranteController;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RistoranteDAO ristoranteDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private RistoratoreDAO ristoratoreDAO;

    @Mock
    private DashboardPersonaleService dashboardPersonaleService;

    private RistoranteController underTest;

    @Autowired
    MockMvc mockMvc;

    private Set<Ristoratore> RisSet;

    private Set<Ristorante> Ristoranti;


    @BeforeEach
    void setUp() {
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
        this.underTest = new RistoranteController(new RistoranteServiceImpl(ristoranteDAO, dashboardPersonaleService));
    }

    @AfterEach
    void tearDown() {
        ristoranteDAO.deleteAll();
        ristoratoreDAO.deleteAll();
    }

    /**
     * Testa l'endpoint di inserimento ristorante effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void insertRistorante() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        userService.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{ \"nome\": \"LinCal Bakery\"," +
                " \"n_Civico\": \"2\", " +
                "\"cap\": 80049, " +
                "\"via\": \"Xipu Street, Pidu District\", " +
                "\"provincia\": \"CD\", " +
                "\"telefono\": \"00393331005060\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/insertRistorante")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(jsonString)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di getRistorante tramite id effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void getRistorante() throws Exception {
        Ristorante ristorante = new Ristorante(10, "Pizzeria Tuino", "12", 80056, "Via E Caianiello", "SA", "003930090004356", null);

        ristoranteDAO.save(ristorante);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/getRistorante/{idRistorante}", ristorante.getId())
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }


    /**
     * Testa l'endpoint di getRistoranti associati ad un ristoratore effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void getAllRistorantiByRistoratore() throws Exception {
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);


        Ristorante ristorante = new Ristorante(1, "Pizzeria Tuino", "12", 80056, "Via E Caianiello", "SA", "003930090004356", RisSet);

        ristoranteDAO.save(ristorante);
        ristoratore.getRistoranti().add(ristorante);

        userService.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/restaurantsList/{idRistoratore}", ristoratore.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());

    }

    /**
     * Testa l'endpoint di modifica dati di un ristorante effettuando una richiesta con
     * parametri validi
     * */
    @Test
    void updateRistorante() throws Exception {
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);


        Ristorante ristorante = new Ristorante(1, "Pizzeria Tuino", "12", 80056, "Via E Caianiello", "SA", "003930090004356", RisSet);

        ristoranteDAO.save(ristorante);
        ristoratore.getRistoranti().add(ristorante);

        userService.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{ \"nome\": \"Prova Cambio\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/updateRistorante/{idRistorante}", ristorante.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(jsonString)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di inserimento ristorante effettuando una richiesta con
     * una mail non associata nessun ristoratore
     * */
    @Test
    void failInsertRistorante() throws Exception {
        //Given
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{ \"nome\": \"LinCal Bakery\"," +
                " \"n_Civico\": \"2\", " +
                "\"cap\": 80049, " +
                "\"via\": \"Xipu Street, Pidu District\", " +
                "\"provincia\": \"CD\", " +
                "\"telefono\": \"00393331005060\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/insertRistorante")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(jsonString)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_UNAUTHORIZED, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di modifica dati di un ristorante effettuando una richiesta con
     * parametri non validi, in particolare il ristoratore non è proprietario del ristorante che si vuole modificare
     * */
    @Test
    void failUpdateRistoranteNotOwner() throws Exception {
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);


        Ristorante ristorante = new Ristorante(1, "Pizzeria Tuino", "12", 80056, "Via E Caianiello", "SA", "003930090004356", RisSet);

        ristoranteDAO.save(ristorante);

        userService.save(ristoratore);

        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{ \"nome\": \"Prova Cambio\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/updateRistorante/{idRistorante}", ristorante.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(jsonString)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_NOT_FOUND, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint di modifica dati di un ristorante effettuando una richiesta con
     * parametri non validi, in particolare la mail non risulta associata ad un ristoratore
     * */
    @Test
    void failUpdateRistoranteUserNotFound() throws Exception {
        Ristoratore ristoratore = new Ristoratore(1, "KimDami98", "Jacopo"
                , "Espsosito", "shen@yue.it", "3510857328", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);



        var tokenString = jwtService.generateToken(ristoratore);

        String jsonString = "{ \"nome\": \"Prova Cambio\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/updateRistorante/{idRistorante}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(jsonString)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_UNAUTHORIZED, result.getResponse().getStatus());
    }

}