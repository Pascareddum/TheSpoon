package it.unisa.thespoon.ordini.service;

import it.unisa.thespoon.model.dao.OrdiniDAO;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.*;
import it.unisa.thespoon.model.request.InsertOrdineRequest;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Jacopo Gennaro Esposito
 * Implementa la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema ordini, che gestice gli ordini
 * */
@Service
@RequiredArgsConstructor
public class OrdiniServiceImpl implements OrdiniService{

    private final RistoranteDAO ristoranteDAO;
    private final RistoratoreDAO ristoratoreDAO;
    private final OrdiniDAO ordiniDAO;

    private final ProdottoService prodottoService;

    /**
     * Metodo per inserire un nuovo ordine
     *
     * @param insertOrdineRequest Oggetto che rappresenta una richiesta di inserimento ordine
     * @return ResponseEntity <Ordine> Response contenente i dettagli dell'ordine
     **/
    @Override
    @Transactional
    public ResponseEntity<Ordine> insertOrdine(InsertOrdineRequest insertOrdineRequest) {
        float totale = 0.0F;
        Byte stato = 0;
        Prodotto prodotto;

        Ordine newOrdine = new Ordine();

        Optional<Ristorante> ristorante = ristoranteDAO.findById(insertOrdineRequest.getIdRistorante());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        newOrdine.setChatId(insertOrdineRequest.getChatID());
        newOrdine.setOra(Time.valueOf(LocalTime.now()));
        newOrdine.setTipologia(insertOrdineRequest.getTipologia());
        newOrdine.setStato(stato);
        newOrdine.setIdristorante(insertOrdineRequest.getIdRistorante());
        newOrdine.setTotale(totale);

        for(Integer product : insertOrdineRequest.getProductsIDs()){
            Optional<Prodotto> newProdotto = prodottoService.getProdotto(product);

            if(newProdotto.isEmpty())
                return new ResponseEntity<>(HttpStatus.OK);
            else {
                prodotto = newProdotto.get();
            }
            totale+=prodotto.getPrezzo();
            ProdottoOrdineID prodottoOrdineID = new ProdottoOrdineID();
            prodottoOrdineID.setIdOrdine(newOrdine.getIdordine());
            prodottoOrdineID.setIdProdotto(prodotto.getId());

            ProdottoOrdine prodottoOrdine = new ProdottoOrdine();
            prodottoOrdine.setId(prodottoOrdineID);
            prodottoOrdine.setProdotto(prodotto);
            prodottoOrdine.setOrdine(newOrdine);

            newOrdine.getProducts().add(prodottoOrdine);
            prodotto.getContainedOrders().add(prodottoOrdine);
        }


        if(insertOrdineRequest.getNumeroTavolo()!=null) {
            newOrdine.setNr_Tavolo(insertOrdineRequest.getNumeroTavolo());
        }

        newOrdine.setTotale(totale);
        ordiniDAO.save(newOrdine);


        return new ResponseEntity<>(newOrdine, HttpStatus.OK);
    }

    /**
     * Firma del metodo per confermare un nuovo ordine
     *
     * @param idOrdine ID dell'ordine che si intende confermare
     * @param email    Email del ristoratore che effettua la richiesta
     * @return ResponseEntity <Ordine> Response contenente i dettagli dell'ordine
     **/
    @Override
    public ResponseEntity<HttpStatus> confermaOrdine(Integer idOrdine, String email) {
        Byte stato = 1;
        Optional<Ordine> ordine = ordiniDAO.findById(idOrdine);
        if(ordine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(ordine.get().getIdristorante(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ordine.get().setStato(stato);
        ordiniDAO.save(ordine.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Firma del metodo per ottenere i dettagli di un ordine
     *
     * @param idRistorante Identificativo dell'ordine
     * @param email
     * @return ResponseEntity <Ordine> Response contenente i dettagli dell'ordine
     **/
    @Override
    public ResponseEntity<List<Ordine>> ordiniByRistorante(Integer idRistorante, String email) {
        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(idRistorante, ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Ordine> ordini = ordiniDAO.findAllByIdRistorante(idRistorante);

        return new ResponseEntity<>(ordini, HttpStatus.OK);
    }

    /**
     * Firma del metodo per ottenere i dettagli dei prodotti
     * associati ad un ordine dato il suo id e l'id del ristorante
     *
     * @param idRistorante Identificativo del ristorante
     * @param idOrdine     Identificativo dell'ordine
     * @param email
     * @return ResponseEntity <List<Prodotto> Response contenente i dettagli dei prodotti associati ad un ordine
     */
    @Override
    public ResponseEntity<List<Prodotto>> getOrdiniByID(Integer idRistorante, Integer idOrdine, String email) {
        Optional<Ordine> ordine = ordiniDAO.findById(idOrdine);
        if(ordine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(ordine.get().getIdristorante(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Prodotto> prodotti = ordiniDAO.getProdottiByIdOrdineAndIdRistorante(idRistorante, idOrdine);

        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }


}
