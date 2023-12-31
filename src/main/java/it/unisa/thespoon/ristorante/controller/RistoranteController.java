package it.unisa.thespoon.ristorante.controller;


import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import it.unisa.thespoon.ristorante.service.RistoranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jacopo Gennaro Esposito
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema Ristorante
 * */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/ristorante")
public class RistoranteController {

    private final RistoranteService ristoranteService;

    @PostMapping("/insertRistorante")
    public ResponseEntity<HttpStatus> InsertRistorante(@Valid @RequestBody InsertRistoranteRequest insertRistoranteRequest, Authentication authentication){
        return ristoranteService.insertRistorante(insertRistoranteRequest, authentication.getName());
    }

}
