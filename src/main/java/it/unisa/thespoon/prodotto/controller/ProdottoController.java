package it.unisa.thespoon.prodotto.controller;

import it.unisa.thespoon.model.entity.Prodotto;
import it.unisa.thespoon.model.request.InsertProdottoRequest;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema Prodotto
 * @author Jacopo Gennaro Esposito
 * */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/prodotto")
public class ProdottoController {

    private final ProdottoService prodottoService;

    @PostMapping("/insertProdotto")
    public ResponseEntity<HttpStatus> InsertProdotto(@Valid @RequestBody InsertProdottoRequest insertProdottoRequest, Authentication authentication){
        return prodottoService.insertProdotto(insertProdottoRequest, authentication.getName());
    }

    @DeleteMapping("/removeProdotto/{id_prodotto}")
    public ResponseEntity<HttpStatus> RemoveProdotto(@PathVariable(value = "id_prodotto") Integer idProdotto){
        return prodottoService.removeProdotto(idProdotto);
    }

    @GetMapping("/getProdotto/{id_prodotto}")
    public ResponseEntity<Prodotto> GetProdotto(@PathVariable(value = "id_prodotto") Integer idProdotto){
        Optional<Prodotto> prodotto = prodottoService.getProdotto(idProdotto);
        if(prodotto.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(prodotto.get(), HttpStatus.OK);
    }

    @GetMapping("/getAllProdottiByIdRistorante/{id_ristorante}")
    public ResponseEntity<List<Prodotto>> GetAllProdottiByIdRistorante(@PathVariable(value = "id_ristorante") Integer idRistorante){
        return prodottoService.getAllProdottiByIdRistorante(idRistorante);
    }
}
