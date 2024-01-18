package it.unisa.thespoon.pagamenti.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 Interfaccia per i metodi del sottosistema Pagamento
 @author Jacopo Gennaro Esposito
 **/
public interface PagamentiService {

    ResponseEntity<String> creaSessionePagamento(Integer idOrdine, Integer idRistoratore) throws StripeException;

    void handleSuccessoPagamento(Session session) throws Exception;
}
