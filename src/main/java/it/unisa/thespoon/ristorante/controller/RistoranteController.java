package it.unisa.thespoon.ristorante.controller;


import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import it.unisa.thespoon.model.request.SearchRistoranteRequest;
import it.unisa.thespoon.model.request.UpdateRistoranteRequest;
import it.unisa.thespoon.ristorante.service.RistoranteService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @GetMapping("/getRistorante/{id_ristorante}")
    public ResponseEntity<Ristorante> GetRistorante(@PathVariable(value = "id_ristorante") Integer id_ristorante){
        return ristoranteService.getRistoranteByID(id_ristorante);
    }

    @GetMapping("/restaurantsList/{id_ristoratore}")
    public ResponseEntity<Set<Ristorante>> GetAllRistorantiByRistoratore(@PathVariable(value = "id_ristoratore") Integer id_ristoratore){
        return ristoranteService.getAllRistorantiByRistoratore(id_ristoratore);
    }

    @PostMapping("/updateRistorante/{id_ristorante}")
    public ResponseEntity<HttpStatus> UpdateRistorante(@Valid @RequestBody UpdateRistoranteRequest updateRistoranteRequest,
                                                       @PathVariable(value = "id_ristorante") Integer idRistorante,
                                                       Authentication authentication){
        return ristoranteService.updateRistorante(updateRistoranteRequest, idRistorante, authentication.getName());
    }

    @GetMapping("/ricercaRistorante/{nome_ristorante}")
    public ResponseEntity<Set<Ristorante>> SearchRistorante(@Valid @RequestBody SearchRistoranteRequest searchRistoranteRequest,
                                                       @PathVariable(value = "nome_ristorante") String nomeRistorante){
        return ristoranteService.searchRistorante(searchRistoranteRequest, nomeRistorante);
    }

    @GetMapping("/ricercaRistorante/")
    public ResponseEntity<Set<Ristorante>> SearchRistorante(@Valid @RequestBody SearchRistoranteRequest searchRistoranteRequest){
        return ristoranteService.searchRistorante(searchRistoranteRequest, null);
    }
}
