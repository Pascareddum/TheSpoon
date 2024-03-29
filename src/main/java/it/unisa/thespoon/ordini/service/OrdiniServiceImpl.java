/**
 * @author Jacopo Gennaro Esposito
 * Implementa la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema ordini, che gestice gli ordini
 * */

package it.unisa.thespoon.ordini.service;

import it.unisa.thespoon.model.dao.OrdiniDAO;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.*;
import it.unisa.thespoon.model.request.InsertOrdineRequest;
import it.unisa.thespoon.model.response.ProdottoOrdineInfo;
import it.unisa.thespoon.notifiche.service.TelegramAdapter;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

/**
 *
 * Classe che implementa i metodi del sottosistema Ordini
 * @author Jacopo Gennaro Esposito
 */
@Service
@RequiredArgsConstructor
public class OrdiniServiceImpl implements OrdiniService{

    private final RistoranteDAO ristoranteDAO;
    private final RistoratoreDAO ristoratoreDAO;
    private final OrdiniDAO ordiniDAO;

    private final ProdottoService prodottoService;
    private final TelegramAdapter telegramAdapter;

    @Autowired
    private OrdineObserverService ordineObserverService;

    /**
     * Metodo per inserire un nuovo ordine
     *
     * @param insertOrdineRequest Oggetto che rappresenta una richiesta di inserimento ordine
     * @return ResponseEntity Response contenente i dettagli dell'ordine
     **/
    @Override
    @Transactional
    public ResponseEntity<Ordine> insertOrdine(InsertOrdineRequest insertOrdineRequest) {
        BigDecimal totale = new BigDecimal(0);
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                prodotto = newProdotto.get();
            }

            Optional<ProdottoOrdine> existingProductOrder = newOrdine.getProducts().stream()
                    .filter(po -> po.getProdotto().getId().equals(newProdotto.get().getId()))
                    .findFirst();

            if (existingProductOrder.isPresent()) {
                existingProductOrder.get().setQuantita(existingProductOrder.get().getQuantita() + 1);
                totale = totale.add(prodotto.getPrezzo());
            } else {

                totale = totale.add(prodotto.getPrezzo());
                ProdottoOrdineID prodottoOrdineID = new ProdottoOrdineID();
                prodottoOrdineID.setIdOrdine(newOrdine.getIdordine());
                prodottoOrdineID.setIdProdotto(prodotto.getId());

                ProdottoOrdine prodottoOrdine = new ProdottoOrdine();
                prodottoOrdine.setId(prodottoOrdineID);
                prodottoOrdine.setProdotto(prodotto);
                prodottoOrdine.setOrdine(newOrdine);
                prodottoOrdine.setQuantita(1);

                newOrdine.getProducts().add(prodottoOrdine);
                prodotto.getContainedOrders().add(prodottoOrdine);
            }
        }


        if(insertOrdineRequest.getNumeroTavolo()!=null) {
            System.out.println("QUI ENTRO");
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
     * @return ResponseEntity Response contenente i dettagli dell'ordine
     **/
    @Override
    public ResponseEntity<HttpStatus> confermaOrdine(Integer idOrdine, String email) {
        Byte stato = 1;
        Optional<Ordine> ordine = ordiniDAO.findById(idOrdine);
        if(ordine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ordine.get().setOrdineObserverService(ordineObserverService);

        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(ordine.get().getIdristorante(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ordine.get().setStato(stato);
        ordiniDAO.save(ordine.get());

        ordine.get().setStato(stato, ristorante.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Firma del metodo per ottenere i dettagli di un ordine
     *
     * @param idRistorante Identificativo dell'ordine
     * @param email
     * @return ResponseEntity Response contenente i dettagli dell'ordine
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
     * @return ResponseEntity Response contenente i dettagli dei prodotti associati ad un ordine
     */
    @Override
    public ResponseEntity<List<ProdottoOrdineInfo>> getProdottiByIdOrdineIdRistorante(Integer idRistorante, Integer idOrdine, String email) {
        Optional<Ordine> ordine = ordiniDAO.findById(idOrdine);
        if(ordine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(ordine.get().getIdristorante(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<ProdottoOrdineInfo> prodotti = ordiniDAO.getProdottiByIdOrdineAndIdRistorante(idRistorante, idOrdine);

        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }

    /**
     * Metodo per ottenere i dettagli di un ordine dato il suo ID e l'id del ristorante
     *
     * @param idOrdine     Identificativo del ristorante
     * @param idRistorante Identificativo dell'ordine
     * @return Ordine
     */
    @Override
    public Optional<Ordine> getOrdineByIdOrdinedAndIdRistorante(Integer idOrdine, Integer idRistorante) {
        return ordiniDAO.getByIdordineAndIdristorante(idOrdine, idRistorante);
    }

    /**
     * Metodo che permette di aggiornare lo stato di un ordine sul db, chiamato
     * da altri sottosistemi
     *
     * @param stato Stato da aggiornare
     * @param ordine Ordine per il quale aggiornare lo stato
     */
    @Override
    public void setStato(Byte stato, Ordine ordine) {
        ordine.setStato(stato);
        ordiniDAO.save(ordine);
    }


}
