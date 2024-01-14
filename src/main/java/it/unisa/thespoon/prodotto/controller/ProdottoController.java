package it.unisa.thespoon.prodotto.controller;

import it.unisa.thespoon.model.request.InsertProdottoRequest;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jacopo Gennaro Esposito
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema Prodotto
 * */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/prodotto")
public class ProdottoController {

    private final ProdottoService prodottoService;

    @PostMapping("/insertProdotto")
    public ResponseEntity<HttpStatus> InsertProdotto(@Valid @RequestBody InsertProdottoRequest insertProdottoRequest){
        return prodottoService.insertProdotto(insertProdottoRequest);
    }

    @DeleteMapping("/removeProdotto/{id_prodotto}")
    public ResponseEntity<HttpStatus> RemoveProdotto(@PathVariable(value = "id_prodotto") Integer idProdotto){
        return prodottoService.removeProdotto(idProdotto);
    }
}
