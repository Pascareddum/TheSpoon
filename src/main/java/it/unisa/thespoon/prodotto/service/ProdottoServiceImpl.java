package it.unisa.thespoon.prodotto.service;

import it.unisa.thespoon.model.dao.ProdottoDAO;

import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Prodotto;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.InsertProdottoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementa la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema prodotto, che gestice i singoli prodotti
 * @author Jacopo Gennaro Esposito
 * */
@Service
@RequiredArgsConstructor
public class ProdottoServiceImpl implements ProdottoService {

    private final ProdottoDAO prodottoDAO;
    private final RistoranteDAO ristoranteDAO;
    private final RistoratoreDAO ristoratoreDAO;


    /**
     * Metodo adibito all'inserimento di un nuovo prodotto nel sistema
     *
     * @param insertProdottoRequest Oggetto che rappresenta una richiesta di inserimento
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     **/
    @Override
    public ResponseEntity<HttpStatus> insertProdotto(InsertProdottoRequest insertProdottoRequest, String email) {
        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(insertProdottoRequest.getIdRistorante(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var prodotto = Prodotto.builder()
                .Nome(insertProdottoRequest.getNome())
                .Descrizione(insertProdottoRequest.getDescrizione())
                .Prezzo(insertProdottoRequest.getPrezzo())
                .idristorante(insertProdottoRequest.getIdRistorante())
                .build();

        prodottoDAO.save(prodotto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo adibito alla rimozione di un nuovo prodotto dal sistema
     *
     * @param Id Identificativo dell'ordine che si intende rimuovere
     * @return ResponseEntity Codice di risposta HTTP
     */
    @Override
    public ResponseEntity<HttpStatus> removeProdotto(Integer Id) {
        Optional<Prodotto> prodotto = prodottoDAO.findSingleProdottoById(Id);

        if(prodotto.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        prodottoDAO.delete(prodotto.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo per recuperare un prodotto dato il suo ID
     *
     * @param Id Identificativo dell'ordine che si intende recuperare
     * @return Optional Optional contenente il prodotto recuperato
     */
    public Optional<Prodotto> getProdotto(Integer Id){
        return prodottoDAO.findById(Id);
    }

    /**
     * Metodo per salvare i prodotti
     * @param prodotto Prodotto che si intende salvare nel db
     * @return Prodotto Istanza del prodotto appena salvata
     */
    @Override
    public Prodotto saveProdotto(Prodotto prodotto) {
        return prodottoDAO.save(prodotto);
    }

    /**
     * Metodo per recuperare una lista di prodotti associati ad un dato ID ristorante
     * @param idRistorante Id del ristorante per il quale si intende recuperare i prodotti
     * @return ResponseEntity contenente la lista di prodotti assciati.
     */
    @Override
    public ResponseEntity<List<Prodotto>> getAllProdottiByIdRistorante(Integer idRistorante) {
        List<Prodotto> prodotti = prodottoDAO.findAllByIdristorante(idRistorante);
        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }
}
