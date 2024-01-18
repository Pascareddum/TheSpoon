package it.unisa.thespoon.pagamenti.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import it.unisa.thespoon.pagamenti.service.PagamentiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema Pagamento
 *  @author Jacopo Gennaro Esposito
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamenti")
public class PagamentiController {

    @Autowired
    private PagamentiService pagamentiService;

    @Value("${WEBHOKSTRIPESECRET}")
    private String webhokSecret;

    @PostMapping("/pay/{id_ordine}/{id_ristorante}")
    public ResponseEntity<String> creaCheckoutSession(@PathVariable(name = "id_ordine") Integer idOrdine, @PathVariable(name = "id_ristorante") Integer idRistorante){
        ResponseEntity<String> sessionID;
        try{
           sessionID = pagamentiService.creaSessionePagamento(idOrdine, idRistorante);
        } catch (StripeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return sessionID;
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader){
        try {
            // Verifica la firma della webhook
            Event event = Webhook.constructEvent(payload, sigHeader, webhokSecret);

            // Gestisci l'evento di pagamento completato
            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                pagamentiService.handleSuccessoPagamento(session);
            }

            return new ResponseEntity<>("Webhook processed successfully", HttpStatus.OK);
        } catch (SignatureVerificationException e) {
            // La firma della webhook non è valida
            return new ResponseEntity<>("Invalid signature", HttpStatus.BAD_REQUEST);
        } catch (StripeException e) {
            // Gestisci eccezioni Stripe
            return new ResponseEntity<>("Error processing webhook", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Ordine non trovato", HttpStatus.NOT_FOUND);
        }
    }
}
