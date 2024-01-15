package it.unisa.thespoon.ristorante.controller;


import it.unisa.thespoon.model.entity.Menu;
import it.unisa.thespoon.model.entity.Prodotto;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Tavolo;
import it.unisa.thespoon.model.request.*;
import it.unisa.thespoon.ristorante.service.RistoranteService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
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

    @PostMapping("/insertMenu")
    public ResponseEntity<HttpStatus> InsertMenu(@Valid @RequestBody InsertMenuRequest insertMenuRequest, Authentication authentication){
        return ristoranteService.insertMenu(insertMenuRequest, authentication.getName());
    }

    @PostMapping("/addProductToMenu/{id_menu}/{id_prodotto}/{id_ristorante}")
    public ResponseEntity<HttpStatus> AddProductToMenu(@PathVariable(value = "id_menu") Integer idMenu, @PathVariable
            (value = "id_prodotto") Integer idProdotto, @PathVariable(value = "id_ristorante") Integer idRistorante,
                                                       Authentication authentication){
        return ristoranteService.addProductToMenu(idMenu, idProdotto, idRistorante, authentication.getName());
    }

    @DeleteMapping("/removeProductMenu/{id_menu}/{id_prodotto}/{id_ristorante}")
    public ResponseEntity<HttpStatus> RemoveProductFromMenu(@PathVariable(value = "id_menu") Integer idMenu,
                                                            @PathVariable(value = "id_prodotto") Integer idProdotto,
                                                            @PathVariable(value = "id_ristorante") Integer idRistorante,
                                                            Authentication authentication){
        return ristoranteService.removeProductFromMenu(idMenu, idProdotto, idRistorante, authentication.getName());
    }

    @GetMapping("/getMenuByIDRistorante/{id_ristorante}")
    public ResponseEntity<Set<Menu>> GetMenuByIdRistorante(@PathVariable(value = "id_ristorante") Integer idRistorante){
        return ristoranteService.getMenusByRistoranteID(idRistorante);
    }

    @GetMapping("/getMenuByID/{id_menu}")
    public ResponseEntity<Menu> GetMenuByIdMenu(@PathVariable(value = "id_menu") Integer idMenu){
        return ristoranteService.getMenusByID(idMenu);
    }

    @GetMapping("/getProdottiByIDMenu/{id_menu}")
    public ResponseEntity<Set<Prodotto>> GetProdottiByIdMenu(@PathVariable(value = "id_menu") Integer idMenu){
        return ristoranteService.getProdottiByMenuID(idMenu);
    }

    @PostMapping("/insertTavolo")
    public ResponseEntity<HttpStatus> InsertTavolo(@Valid @RequestBody InsertTavoloRequest insertTavoloRequest, Authentication authentication){
        return ristoranteService.insertTavolo(insertTavoloRequest, authentication.getName());
    }

    @GetMapping("/getTavoliRistorante/{id_ristorante}")
    public ResponseEntity<Set<Tavolo>> GetTavoliRistorante(@PathVariable(value = "id_ristorante") Integer idRistorante){
        return ristoranteService.getTavoliRistorante(idRistorante);
    }

    @GetMapping("/getTavoloById/{id_tavolo}/{id_ristorante}")
    public ResponseEntity<Tavolo> GetTavoloByID(@PathVariable(value = "id_tavolo") String idTavolo, @PathVariable(value = "id_ristorante")
                                                Integer idRistorante){
        return ristoranteService.getTavoloByID(idTavolo, idRistorante);
    }
}
