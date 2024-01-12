package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.login.service.UserService;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import it.unisa.thespoon.model.request.UpdateRistoranteRequest;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class RistoranteServiceImplTest {

    @Autowired
    private RistoranteDAO ristoranteDAO;

    @Autowired
    private UserService userService;

    private RistoranteServiceImpl underTest;

    @Autowired
    private DashboardPersonaleService dashboardPersonaleService;

    private Set<Ristoratore> RisSet;

    private Set<Ristorante> Ristoranti;
    @BeforeEach
    void setUp(){
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
        this.underTest = new RistoranteServiceImpl(ristoranteDAO, dashboardPersonaleService);
    }

    @AfterEach
    void tearDown(){
        ristoranteDAO.deleteAll();
        RisSet = new HashSet<Ristoratore>();
        Ristoranti = new HashSet<Ristorante>();
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