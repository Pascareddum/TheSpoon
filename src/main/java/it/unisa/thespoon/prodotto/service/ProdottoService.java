package it.unisa.thespoon.prodotto.service;

import it.unisa.thespoon.model.entity.Prodotto;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.InsertProdottoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 @author Jacopo Gennaro Esposito
 Interfaccia per i metodi del sottosistema Prodotto
 **/
public interface ProdottoService {
    ResponseEntity<HttpStatus> insertProdotto(InsertProdottoRequest insertProdottoRequest);

    ResponseEntity<HttpStatus> removeProdotto(Integer Id);

    public Optional<Prodotto> getProdotto(Integer Id);

    public Prodotto saveProdotto(Prodotto prodotto);
}
