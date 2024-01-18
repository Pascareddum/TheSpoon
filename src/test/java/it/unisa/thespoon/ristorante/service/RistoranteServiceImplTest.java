package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.MenuDAO;
import it.unisa.thespoon.model.dao.ProdottoDAO;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.TavoloDAO;
import it.unisa.thespoon.model.entity.*;
import it.unisa.thespoon.model.request.InsertMenuRequest;
import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import it.unisa.thespoon.model.request.InsertTavoloRequest;
import it.unisa.thespoon.model.request.UpdateRistoranteRequest;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test per la classe Ristorante
 * */
@SpringBootTest
@Transactional
@DirtiesContext
@ExtendWith(MockitoExtension.class)
class RistoranteServiceImplTest {

    @Autowired
    private RistoranteDAO ristoranteDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private TavoloDAO tavoloDAO;

    @Autowired
    private ProdottoDAO prodottoDAO;

    @Autowired
    private UserService userService;

    private RistoranteServiceImpl underTest;

    @Autowired
    private DashboardPersonaleService dashboardPersonaleService;

    @Autowired
    private ProdottoService prodottoService;

    private Set<Ristoratore> RisSet;

    private Set<Ristorante> Ristoranti;

    private Set<Tavolo> Tables;

    private Set<Menu> Menus;
    @BeforeEach
    void setUp(){
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
        Tables = new HashSet<Tavolo>();
        Menus = new HashSet<Menu>();
        this.underTest = new RistoranteServiceImpl(ristoranteDAO, menuDAO, tavoloDAO, dashboardPersonaleService, prodottoService);
    }

    @AfterEach
    void tearDown(){
        ristoranteDAO.deleteAll();
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
        Tables = new HashSet<Tavolo>();
        Menus = new HashSet<Menu>();
    }

    /** Testa la funzionalità di inserimento di un nuovo ristorante
     * all'interno della piattaforma.
     */
    @Test
    void insertRistorante() {
        InsertRistoranteRequest insertRistoranteRequest = new InsertRistoranteRequest("Pizzeria Civico 7", "7", 84084,
                "Giovanni De Martino", "SA", "3339001059");

        Ristoratore ristoratore = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        dashboardPersonaleService.saveRistoratore(ristoratore);

        //When
        ResponseEntity<HttpStatus> response = underTest.insertRistorante(insertRistoranteRequest, "shen@yue.it");

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /** Testa la funzionalità di modifica dei dettagli di un ristorante
     * all'interno della piattaforma.
     */
    @Test
    void updateRistorante() {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        ristoranteDAO.save(ristorante);
        proprietario.getRistoranti().add(ristorante);

        dashboardPersonaleService.saveRistoratore(proprietario);


        //When
        UpdateRistoranteRequest updateRistoranteRequest = new UpdateRistoranteRequest(null, null, 80048, null, null, null);
        ResponseEntity<HttpStatus> response = underTest.updateRistorante(updateRistoranteRequest, ristorante.getId(), "shen@yue.it");

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateRistoranteRequest.getCap(), ristoranteDAO.findById(ristorante.getId()).get().getCap());
    }

    /** Testa la funzionalità che restituisce la lista dei ristoranti
     * associati ad un unico ristoratore
     */
    @Test
    void getAllRistorantiByRistoratore() {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet);
        Ristorante ristorante2 = new Ristorante(2, "Pizzeria Al Vicolo", "7", 84084, "Via Roma", "SA", "3339001212", RisSet);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);

        ristoranteDAO.save(ristorante);
        ristoranteDAO.save(ristorante2);

        proprietario.getRistoranti().add(ristorante);
        proprietario.getRistoranti().add(ristorante2);


        dashboardPersonaleService.saveRistoratore(proprietario);

        //When
        ResponseEntity<Set<Ristorante>> response = underTest.getAllRistorantiByRistoratore(proprietario.getId());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ristorante.getNome(), response.getBody().iterator().next().getNome());
    }

    /** Testa la funzionalità che restituisce di un ristorante
     * dato il suo identificativo
     */
    @Test
    void getRistoranteByID() {
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet);
        ristoranteDAO.save(ristorante);

        //When
        ResponseEntity<Ristorante> response = underTest.getRistoranteByID(ristorante.getId());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ristorante.getNome(), response.getBody().getNome());
    }

    /**
     * Testa la funzionalità di inserimento di un menu in un ristorante
     */
    @Test
    void insertMenu(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        ristoranteDAO.save(ristorante);
        proprietario.getRistoranti().add(ristorante);

        dashboardPersonaleService.saveRistoratore(proprietario);

        //When
        ResponseEntity<HttpStatus> response = underTest.insertMenu(new InsertMenuRequest("Menu Pizza", "Pizza", ristorante.getId()), proprietario.getEmail());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /** Testa la funzionalità di aggiunta di un prodotto ad un menu
     * con parametri validi
     */
    @Test
    void addProductToMenu(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        Prodotto pro = prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);

        proprietario.getRistoranti().add(ristorante);
        ristorante.getOwners().add(proprietario);
        ristorante.getMenus().add(newMenu);

        Ristorante ris = ristoranteDAO.save(ristorante);
        dashboardPersonaleService.saveRistoratore(proprietario);

        //when
        ResponseEntity<HttpStatus> response = underTest.addProductToMenu(menu.getId(), pro.getId(), ris.getId(), "shen@yue.it");

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Testa la funzionalità di rimozione di un prodotto dal menu
     *
     */
    @Test
    void removeProductFromMenu(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);


        proprietario.getRistoranti().add(ristorante);
        ristorante.getOwners().add(proprietario);
        ristorante.getMenus().add(newMenu);
        newProdotto.getContained().add(newMenu);


        Ristorante ris = ristoranteDAO.save(ristorante);
        Prodotto np = prodottoDAO.save(newProdotto);
        dashboardPersonaleService.saveRistoratore(proprietario);

        //When
        ResponseEntity<HttpStatus> response = underTest.removeProductFromMenu(menu.getId(), np.getId(), ris.getId(), "shen@yue.it");

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Testa la funzionalità per ottenere i menu associati ad un dato ristorante
     * */
    @Test
    void getMenusByRestaurantID(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Ristoratore proprietario = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Prodotto newProdotto = new Prodotto(1, "Pizza", "Pizza Fritta", new BigDecimal(3.30), Menus);
        Menu newMenu = new Menu(1, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        prodottoDAO.save(newProdotto);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);


        proprietario.getRistoranti().add(ristorante);
        ristorante.getOwners().add(proprietario);
        ristorante.getMenus().add(newMenu);
        newProdotto.getContained().add(newMenu);


        Ristorante ris = ristoranteDAO.save(ristorante);
        Prodotto np = prodottoDAO.save(newProdotto);
        dashboardPersonaleService.saveRistoratore(proprietario);

        //When
        ResponseEntity<Set<Menu>> response = underTest.getMenusByRistoranteID(ris.getId());

        //Then
        assertEquals(menu.getNome(), response.getBody().iterator().next().getNome());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    /**
     * Testa la funzionalità per ottenere un menu dato il suo ID
     */
    @Test
    void MenusByID(){
        Ristorante ristorante = new Ristorante(3, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Menu newMenu = new Menu(3, "Menu Pizzeria", "Menu Pizzeria", new HashSet<Prodotto>(), ristorante);

        ristoranteDAO.save(ristorante);
        newMenu.setRistorante(ristorante);
        Menu menu = menuDAO.save(newMenu);

        //When
        ResponseEntity<Menu> response = underTest.getMenusByID(menu.getId());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menu.getNome(), response.getBody().getNome());
    }

    /**
     * Testa la funzionalità per ottenere la lista di prodotti associati ad un menu dato il suo ID
     */
    @Test
    void prodottiByIDMenu(){
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

        //When
        ResponseEntity<Set<Prodotto>> response = underTest.getProdottiByMenuID(menu.getId());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newProdotto.getNome(), response.getBody().iterator().next().getNome());
    }

    /**
     * Testa la funzionalità per aggiungere un tavolo ad un ristorante
     */
    @Test
    void insertTavolo(){
        Ristoratore ristoratore = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);

        ristoratore.getRistoranti().add(ristorante);
        ristorante.getOwners().add(ristoratore);
        dashboardPersonaleService.saveRistoratore(ristoratore);
        ristoranteDAO.save(ristorante);

        //When
        ResponseEntity<HttpStatus> response = underTest.insertTavolo(new InsertTavoloRequest("1", null, 2, ristorante.getId()), ristoratore.getEmail());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Testa la funzionalità per recuperare i tavoli associati ad un ristorante
     */
    @Test
    void TavoliRistorante(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Tavolo tavolo = new Tavolo("1", null, 2, ristorante);

        ristorante.getTables().add(tavolo);
        ristoranteDAO.save(ristorante);
        tavoloDAO.save(tavolo);

        //When
        ResponseEntity<Set<Tavolo>> response = underTest.getTavoliRistorante(ristorante.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tavolo.getNumeroTavolo(), response.getBody().iterator().next().getNumeroTavolo());
    }

    /**
     * Testa la funzionalità per ottenere i dettagli di un tavolo dato il suo ID
     */
    @Test
    void TavoloByID(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet, Menus, Tables);
        Tavolo tavolo = new Tavolo("1", null, 2, ristorante);

        ristorante.getTables().add(tavolo);
        Ristorante ris = ristoranteDAO.save(ristorante);
        tavoloDAO.save(tavolo);
        //When
        ResponseEntity<Tavolo> response = underTest.getTavoloByID(tavolo.getNumeroTavolo(), ris.getId());

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tavolo.getNumeroTavolo(), response.getBody().getNumeroTavolo());
    }
    /**
     * Testa la funzionalità di inserimento ristorante inserendo una mail non associata
     * a nessun ristoratore
     * */
    @Test
    void failInsertRistorante(){
        InsertRistoranteRequest insertRistoranteRequest = new InsertRistoranteRequest("Pizzeria Civico 7", "7", 84084,
                "Giovanni De Martino", "SA", "3339001059");
        //When
        try {
            ResponseEntity<HttpStatus> response = underTest.insertRistorante(insertRistoranteRequest, "shen@yue.it");
        } catch (Exception e){
            //Then
            assertEquals(e.getLocalizedMessage(), "Account not registered at The Spoon");
        }
    }

    /**
     * Testa la funzionalità di modifica dati del ristorante inserendo una mail non associata
     * al proprietaro del ristorante che si intende modificare
     * */
    @Test
    void failedUpdateRistoranteNotOwner(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet);
        Ristoratore ristoratore = new Ristoratore(1, "ShenYuePass", "Yue", "Shen", "shen@yue.it", "0000000000", LocalDate.now(), Role.ROLE_RISTORATORE, Ristoranti);
        ristoranteDAO.save(ristorante);
        dashboardPersonaleService.saveRistoratore(ristoratore);

        //When
        UpdateRistoranteRequest updateRistoranteRequest = new UpdateRistoranteRequest(null, null, 80048, null, null, null);
        ResponseEntity<HttpStatus> response = underTest.updateRistorante(updateRistoranteRequest, 1, "shen@yue.it");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Testa la funzionalità di modifica dati del ristorante inserendo una mail non associata
     * a nessun account registrato
     * */
    @Test
    void failedUpdateRistoratoreUserNotFound(){
        Ristorante ristorante = new Ristorante(1, "Pizzeria Civico 7", "7A", 84084, "Giovanni De Martino", "SA", "3339001212", RisSet);
        ristoranteDAO.save(ristorante);

        //When
        UpdateRistoranteRequest updateRistoranteRequest = new UpdateRistoranteRequest(null, null, 80048, null, null, null);

        try {
            ResponseEntity<HttpStatus> response = underTest.updateRistorante(updateRistoranteRequest, 1, "shen@yue.it");
        } catch (Exception e){
            //Then
            assertEquals(e.getLocalizedMessage(), "Account not registered at The Spoon");
        }
    }


}