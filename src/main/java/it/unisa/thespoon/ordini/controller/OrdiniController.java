package it.unisa.thespoon.ordini.controller;

import it.unisa.thespoon.model.entity.Ordine;
import it.unisa.thespoon.model.request.InsertOrdineRequest;
import it.unisa.thespoon.model.response.ProdottoOrdineInfo;
import it.unisa.thespoon.ordini.service.OrdiniService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema Ordine
 *  @author Jacopo Gennaro Esposito
 * */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/ordini")
public class OrdiniController {
    private final OrdiniService ordiniService;

    @PostMapping("/insertOrdine")
    public ResponseEntity<Ordine> InsertOrdine(@Valid @RequestBody InsertOrdineRequest insertOrdineRequest){
        return ordiniService.insertOrdine(insertOrdineRequest);
    }

    @PostMapping("/confermaOrdine/{id_ordine}")
    public ResponseEntity<HttpStatus> ConfermaOrdine(@PathVariable(name = "id_ordine") Integer idOrdine, Authentication authentication){
        return ordiniService.confermaOrdine(idOrdine, authentication.getName());
    }

    @GetMapping("/ordiniByRistorante/{id_ristorante}")
    public ResponseEntity<List<Ordine>> OrdiniByRistorante(@PathVariable(name = "id_ristorante") Integer idRistorante, Authentication authentication){
        return ordiniService.ordiniByRistorante(idRistorante, authentication.getName());
    }

    @GetMapping("/prodottiByIdRisAndIdOrd/{id_ristorante}/{id_ordine}")
    public ResponseEntity<List<ProdottoOrdineInfo>> GetOrdiniByID(@PathVariable(name = "id_ristorante") Integer idRistorante,
                                                                  @PathVariable(name = "id_ordine") Integer idOrdine,
                                                                  Authentication authentication){
        return ordiniService.getProdottiByIdOrdineIdRistorante(idRistorante, idOrdine, authentication.getName());
    }
}
