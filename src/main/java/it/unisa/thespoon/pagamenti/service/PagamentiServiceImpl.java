package it.unisa.thespoon.pagamenti.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.ordini.service.OrdiniService;

import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * Classe che implementa i metodi del sottosistema Pagamento
 * @author Jacopo Gennaro Esposito
 */
@Service
@RequiredArgsConstructor
public class PagamentiServiceImpl implements PagamentiService {
    private final OrdiniService ordiniService;
    private final RistoranteDAO ristoranteDAO;

    @Value("${STRIPEKEY}")
    String stripeKey;

    public ResponseEntity<String> creaSessionePagamento(Integer idOrdine, Integer idRistorante) throws StripeException {
        // Recupera l'ordine utilizzando l'ID dall'altro servizio o da un repository
        Optional<Ordine> ordine = ordiniService.getOrdineByIdOrdinedAndIdRistorante(idOrdine, idRistorante);
        Optional<Ristorante> ristorante = ristoranteDAO.findById(idRistorante);

        Stripe.apiKey = stripeKey;

        if(ordine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Map<String, Object> productParams = new HashMap<>();
        productParams.put("id", ordine.get().getIdordine());
        productParams.put("name", ristorante.get().getNome());
        productParams.put("description", "Numero ordine: " + ordine.get().getIdordine());

        com.stripe.model.Product product = com.stripe.model.Product.create(productParams);

        PriceData priceData = PriceData.builder()
                .setProduct(product.getId())
                .setUnitAmount(ordine.get().getTotale().multiply(BigDecimal.valueOf(100)).longValue())
                .setCurrency(Currency.getInstance("EUR").getCurrencyCode())
                .build();


        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(priceData)
                                .setQuantity(1L)
                                .build()
                )
                .setSuccessUrl("https://tuo-sito.com/success")
                .setCancelUrl("https://tuo-sito.com/cancel")
                .putMetadata("restaurant_name", ristorante.get().getNome())
                .putMetadata("restaurant_address", ristorante.get().getVia())
                .putMetadata("restaurant_id", ristorante.get().getId().toString())
                .putMetadata("order_id", ordine.get().getIdordine().toString())
                .build();

        Session session = Session.create(params);

        return new ResponseEntity<>(session.getId(), HttpStatus.OK);
    }

    /**
     * @param session
     */
    @Override
    public void handleSuccessoPagamento(Session session) throws Exception {
        Byte stato = 2;
        Optional<Ordine> Ordine = ordiniService.getOrdineByIdOrdinedAndIdRistorante(Integer.valueOf(session.getMetadata().get("order_id")),
                Integer.valueOf(session.getMetadata().get("restaurant_id")));

        if(Ordine.isEmpty())
            throw new Exception("Ordine non trovato");

        ordiniService.setStato(stato, Ordine.get());

        String paymentIntentID = session.getPaymentIntent();
    }
}
