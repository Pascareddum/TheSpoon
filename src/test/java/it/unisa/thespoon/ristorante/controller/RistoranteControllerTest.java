package it.unisa.thespoon.ristorante.controller;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.exceptionhandler.RestExceptionHandler;
import it.unisa.thespoon.jwt.service.JwtService;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.*;
import it.unisa.thespoon.model.entity.*;
import it.unisa.thespoon.prodotto.service.ProdottoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
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
    private MenuDAO menuDAO;

    @Autowired
    private TavoloDAO tavoloDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private RistoratoreDAO ristoratoreDAO;

    @Autowired
    private ProdottoDAO prodottoDAO;

    @Autowired
    private ProdottoService prodottoService;

    @Mock
    private DashboardPersonaleService dashboardPersonaleService;

    private RistoranteController underTest;

    @Autowired
    MockMvc mockMvc;

    private Set<Ristoratore> RisSet;

    private Set<Ristorante> Ristoranti;

    private Set<Tavolo> Tables;

    private Set<Menu> Menus;


    @BeforeEach
    void setUp() {
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
        Tables = new HashSet<Tavolo>();
        Menus = new HashSet<Menu>();
        this.underTest = new RistoranteController(new RistoranteServiceImpl(ristoranteDAO, menuDAO, tavoloDAO, dashboardPersonaleService, prodottoService));
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
     * Testa l'endpoint per aggiungere un menu ad un ristorante effetuando una
     * richiesta con parametri validi
     * */
    @Test
    void AddMenu() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);

        ristoranteDAO.save(ristorante);
        proprietario.getRistoranti().add(ristorante);

        userService.save(proprietario);

        var tokenString = jwtService.generateToken(proprietario);

        String json = String.format("{\"nome\": \"Menu alla carta\", \"descrizione\": \"Pay as you eat\", \"idRistorante\": %d}", ristorante.getId());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/ristorante/insertMenu")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(json)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint per aggiungere un prodotto al Menu effettuando una richiesta con
     * parametri validi
     */
    @Test
    void addProductToMenu() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        Prodotto pro = prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);

        proprietario.getRistoranti().add(ristorante);
        ristorante.getMenus().add(newMenu);

        Ristorante ris = ristoranteDAO.save(ristorante);
        userService.save(proprietario);

        var tokenString = jwtService.generateToken(proprietario);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/addProductToMenu/{idMenu}/{idProdotto}/{idRistorante}", menu.getId(), pro.getId(), ristorante.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /** Testa l'endpoint di rimozione di un prodotto dal menu
     * effettuando una richiesta con parametri validi
     */
    @Test
    void removeProductFromMenu() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);


        proprietario.getRistoranti().add(ristorante);
        ristorante.getMenus().add(newMenu);
        newProdotto.getContained().add(newMenu);


        Ristorante ris = ristoranteDAO.save(ristorante);
        Prodotto np = prodottoDAO.save(newProdotto);
        userService.save(proprietario);

        var tokenString = jwtService.generateToken(proprietario);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ristorante/removeProductMenu/{idMenu}/{idProdotto}/{idRistorante}", menu.getId(), np.getId(), ristorante.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /** Testa l'endpoint per ottenere i menu associati ad un ristorante
     * effettuando una richiesta con parametri validi
     */
    @Test
    void getMenusByRestaurantID() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);


        proprietario.getRistoranti().add(ristorante);
        ristorante.getMenus().add(newMenu);
        newProdotto.getContained().add(newMenu);


        Ristorante ris = ristoranteDAO.save(ristorante);
        Prodotto np = prodottoDAO.save(newProdotto);
        userService.save(proprietario);

        var tokenString = jwtService.generateToken(proprietario);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/getMenuByIDRistorante/{idRistorante}", ristorante.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint per ottenere i dettagli di un menu dato il suo id
     * effettuando una richiesta con parametri validi
     */
    @Test
    void getMenuByID() throws Exception {
        Ristorante ristorante = new Ristorante(3, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Menu newMenu = new Menu(3, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/getMenuByID/{idMenu}", menu.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa la funzionalità per aggiungere un tavolo ad un ristorante
     */
    @Test
    void insertTavolo() throws Exception {
        Ristoratore ristoratore = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);

        ristoratore.getRistoranti().add(ristorante);
        userService.save(ristoratore);
        ristoranteDAO.save(ristorante);

        var tokenString = jwtService.generateToken(ristoratore);

        String json = String.format("{\"numeroTavolo\": \"2\", \"stato\": \"0\", \"capacita\": 4, \"idRistorante\": %d}", ristorante.getId());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ristorante/insertTavolo")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .content(json)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());

    }

    /**
     * Testa l'endpoint per ottenere la lista di prodotti
     * associati ad un menu dato il suo ID, effettuando una richiesta con parametri validi
     */
    @Test
    void prodottiByIDMenu() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        newMenu.getProdottiMenu().add(newProdotto);
        Menu menu = menuDAO.save(newMenu);


        ristorante.getMenus().add(newMenu);
        newProdotto.getContained().add(newMenu);


        ristoranteDAO.save(ristorante);
        prodottoDAO.save(newProdotto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/getProdottiByIDMenu/{idMenu}", menu.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint per recuperare i tavoli associati ad un ristorante
     * effettuando una richiesta con parametri validi.
     */
    @Test
    void TavoliRistorante() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Tavolo tavolo = new Tavolo("1", null, 2, ristorante);

        ristorante.getTables().add(tavolo);
        ristoranteDAO.save(ristorante);
        tavoloDAO.save(tavolo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/getTavoliRistorante/{idRistorante}", ristorante.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        //When
        MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

        //Then
        assertEquals(MockHttpServletResponse.SC_OK, result.getResponse().getStatus());
    }

    /**
     * Testa l'endpoint per ottenere i dettagli di un tavolo dato il suo ID
     * effettuando una richiesta con parametri validi
     */
    @Test
    void TavoloByID() throws Exception {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Tavolo tavolo = new Tavolo("1", null, 2, ristorante);

        ristorante.getTables().add(tavolo);
        Ristorante ris = ristoranteDAO.save(ristorante);
        tavoloDAO.save(tavolo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ristorante/getTavoloById/{idTavolo}/{idRistorante}", tavolo.getNumeroTavolo(), ris.getId())
                .accept(MediaType.APPLICATION_JSON)
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