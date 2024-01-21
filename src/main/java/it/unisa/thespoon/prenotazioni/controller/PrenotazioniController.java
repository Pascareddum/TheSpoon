package it.unisa.thespoon.prenotazioni.controller;

import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.model.entity.Tavolo;
import it.unisa.thespoon.model.request.InsertPrenotazioneRequest;
import it.unisa.thespoon.model.request.UpdatePrenotazioneRequest;
import it.unisa.thespoon.model.response.PrenotazioneInfo;
import it.unisa.thespoon.prenotazioni.service.PrenotazioniService;
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
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    private final PrenotazioniService prenotazioniService;

    @PostMapping("/insertPrenotazione")
    public ResponseEntity<Prenotazione> InsertPrenotazione(@Valid @RequestBody InsertPrenotazioneRequest insertPrenotazioneRequest){
        return prenotazioniService.insertPrenotazioen(insertPrenotazioneRequest);
    }

    @PostMapping("/updatePrenotazione")
    public ResponseEntity<Prenotazione> UpdatePrenotazione(@Valid @RequestBody UpdatePrenotazioneRequest updatePrenotazioneRequest){
        return prenotazioniService.updatePrenotazione(updatePrenotazioneRequest);
    }

    @GetMapping("/getAllPrenotazioni/{id_ristorante}")
    public ResponseEntity<List<PrenotazioneInfo>> GetAllPrenotazioni(@PathVariable(name = "id_ristorante") Integer idRistorante, Authentication authentication){
        return prenotazioniService.getAllPrenotazioni(idRistorante, authentication.getName());
    }

    @GetMapping("/getAllTavoliByIdPrenotazione/{id_prenotazione}")
    public ResponseEntity<List<Tavolo>> GetAllTavoliByIDPrenotazione(@PathVariable(name = "id_prenotazione") Integer idPrenotazione, Authentication authentication){
        return prenotazioniService.getAllTavoliByIDPrenotazione(idPrenotazione, authentication.getName());
    }

    @PostMapping("/confermaPrenotazione/{id_prenotazione}")
    public ResponseEntity<HttpStatus> ConfermaPrenotazione(@PathVariable(name = "id_prenotazione") Integer idPrenotazione, Authentication authentication){
        return prenotazioniService.confermaPrenotazione(idPrenotazione, authentication.getName());
    }
}
