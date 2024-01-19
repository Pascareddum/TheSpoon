package it.unisa.thespoon.ordini.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import it.unisa.thespoon.model.dao.OrdiniDAO;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.*;
import it.unisa.thespoon.model.request.InsertOrdineRequest;
import it.unisa.thespoon.model.response.ProdottoOrdineInfo;
import it.unisa.thespoon.notifiche.service.TelegramAdapter;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il test per la classe Ordini
 * */
public class OrdiniServiceImplTest {

    private OrdiniServiceImpl ordiniService;
    private RistoranteDAO ristoranteDAO;
    private RistoratoreDAO ristoratoreDAO;
    private OrdiniDAO ordiniDAO;
    private ProdottoService prodottoService;
    private TelegramAdapter telegramAdapter;
    private OrdineObserverService ordineObserverService;

    @BeforeEach
    void setUp() {
        ristoranteDAO = mock(RistoranteDAO.class);
        ristoratoreDAO = mock(RistoratoreDAO.class);
        ordiniDAO = mock(OrdiniDAO.class);
        prodottoService = mock(ProdottoService.class);
        telegramAdapter = mock(TelegramAdapter.class);
        ordineObserverService = mock(OrdineObserverService.class);

        ordiniService = new OrdiniServiceImpl(
                ristoranteDAO,
                ristoratoreDAO,
                ordiniDAO,
                prodottoService,
                telegramAdapter
        );

    }

    /**
     * Test per la funzionalità di inserimento ordine
     * effettuando una richiesta con parametri validi
     * */
    @Test
    void insertOrdine() {
        // Given
        InsertOrdineRequest request = createValidInsertOrdineRequest();
        when(ristoranteDAO.findById(any())).thenReturn(Optional.of(new Ristorante()));
        when(prodottoService.getProdotto(any())).thenReturn(Optional.of(createProdotto()));

        // When
        ResponseEntity<Ordine> response = ordiniService.insertOrdine(request);

        // Then
        assert response.getStatusCode() == HttpStatus.OK;
        assertEquals(BigDecimal.valueOf(5.50), response.getBody().getTotale());
        assertEquals(1, response.getBody().getProducts().iterator().next().getQuantita());
    }

    /**
     * Test per la funzionalità di inserimento ordine
     * effettuando una richiesta inserendo con un IdRistorante non
     * associato ad un ristorante presente nella piattaforma
     * */
    @Test
    void failedInsertOrdineRistoranteNotFound() {
        // Given
        InsertOrdineRequest request = createValidInsertOrdineRequest();
        when(ristoranteDAO.findById(any())).thenReturn(Optional.empty());

        // When
        ResponseEntity<Ordine> response = ordiniService.insertOrdine(request);

        // Then
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    /** Testa la funzionalità di conferma ordine
     * inserendo parametri validi
     */
    @Test
    void confermaOrdine() {
        // Given
        Integer orderId = 1;
        String email = "test@example.com";
        when(ordiniDAO.findById(any())).thenReturn(Optional.of(new Ordine()));
        when(ristoratoreDAO.findByEmail(any())).thenReturn(Optional.of(new Ristoratore()));
        when(ristoranteDAO.findByIdAndAndOwnersID(any(), any())).thenReturn(Optional.of(new Ristorante()));

        // When
        ResponseEntity<HttpStatus> response = ordiniService.confermaOrdine(orderId, email);

        // Then
        assert response.getStatusCode() == HttpStatus.OK;
    }

    /**
     * Testa la funzionalità di conferma ordine
     * inserendo un IDOrdine che non corrisponde a nesun
     * ordine presente nel sistema
     */
    @Test
    void failedConfermaOrdineOrdineNotFound() {
        Integer orderId = 1;
        String email = "test@example.com";
        when(ordiniDAO.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = ordiniService.confermaOrdine(orderId, email);

        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    /**
     * Testa la funzionalità di inserimento ordine
     * inserendo un IDProdotto che non corrisponde a nesun
     * prodotto presente nel sistema
     */
    @Test
    void failedInsertOrdineProductNotFound() {
        // Given
        InsertOrdineRequest request = createValidInsertOrdineRequest();
        when(ristoranteDAO.findById(any())).thenReturn(Optional.of(new Ristorante()));
        when(prodottoService.getProdotto(any())).thenReturn(Optional.empty());

        // When
        ResponseEntity<Ordine> response = ordiniService.insertOrdine(request);

        // Then
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    /**
     * Testa la funzionalità di recupero ordini di
     * un ristorante inserendo una mail non associata ad un ristoratore
     */
    @Test
    void failedOrdiniByRistoranteRistoratoreNotFound() {
        // Given
        Integer idRistorante = 1;
        String email = "nonexistent@example.com";
        when(ristoratoreDAO.findByEmail(any())).thenReturn(Optional.empty());

        // When
        ResponseEntity<List<Ordine>> response = ordiniService.ordiniByRistorante(idRistorante, email);

        // Then
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    /**
     * Testa la funzionalità di recupero prodotti di
     * un ordine inserendo un IDOrdine e un IDRistorante non validi
     */
    @Test
    void failedGetProdottiByIdOrdineIdRistoranteOrdineNotFound() {
        // Given
        Integer idRistorante = 1;
        Integer idOrdine = 1;
        String email = "test@example.com";
        when(ordiniDAO.findById(any())).thenReturn(Optional.empty());

        // When
        ResponseEntity<List<ProdottoOrdineInfo>> response = ordiniService.getProdottiByIdOrdineIdRistorante(idRistorante, idOrdine, email);

        // Then
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }


    private InsertOrdineRequest createValidInsertOrdineRequest() {
        Byte tipologia = 0;
        List<Integer> productsID = new ArrayList<>();
        productsID.add(1);
        InsertOrdineRequest request = new InsertOrdineRequest(productsID, null, 11122, 16, tipologia);
        return request;
    }

    private Prodotto createProdotto() {
        List<ProdottoOrdine> containedOrders = new ArrayList<>();
        Prodotto prodotto = new Prodotto(1, "Pizza Fritta", "Pizza fritta con cicoli e ricotta", BigDecimal.valueOf(5.50), null, containedOrders);
        return prodotto;
    }

}
