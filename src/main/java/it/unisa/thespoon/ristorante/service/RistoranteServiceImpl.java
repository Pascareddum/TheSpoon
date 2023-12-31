package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jacopo Gennaro Esposito
 * Impmenta la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema ristorante, che gestice tutti gli aspetti inerenti i ristoranti
 * */
@Service
@RequiredArgsConstructor
public class RistoranteServiceImpl implements RistoranteService{

    private final RistoranteDAO ristoranteDAO;
    private final RistoratoreDAO ristoratoreDAO;

    /**
     * Metodo adibito all'inserimento di un nuovo ristorante
     *
     * @param insertRistoranteRequest Oggetto che rappresenta una richiesta di inserimento
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     **/
    @Override
    public ResponseEntity<HttpStatus> insertRistorante(InsertRistoranteRequest insertRistoranteRequest, String email) {
        System.out.println(email);
        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(email);

        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var ristorante = Ristorante
                .builder()
                .Nome(insertRistoranteRequest.getNome())
                .Via(insertRistoranteRequest.getVia())
                .N_Civico(insertRistoranteRequest.getN_Civico())
                .Cap(insertRistoranteRequest.getCap())
                .Provincia(insertRistoranteRequest.getProvincia())
                .Telefono(insertRistoranteRequest.getTelefono())
                .build();

        Ristorante newRistorante = ristoranteDAO.save(ristorante);
        ristoratore.get().getRistoranti().add(newRistorante);
        ristoratoreDAO.save(ristoratore.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
