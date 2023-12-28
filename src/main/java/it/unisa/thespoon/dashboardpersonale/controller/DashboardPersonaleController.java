package it.unisa.thespoon.dashboardpersonale.controller;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.UpdatePasswordRequest;
import it.unisa.thespoon.model.request.UpdateRistoratoreRequest;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jacopo Gennaro Esposito
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema Dashboard Personale
 * */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardPersonaleController {
    private final DashboardPersonaleService dashboardPersonaleService;

    @GetMapping("/ristoratoreDetails")
    public ResponseEntity<Ristoratore.RistoratoreDataDisplay> GetRistoratoreDetails(Authentication authentication){
        return dashboardPersonaleService.getRistoratoreDetails(authentication.getName());
    }

    @PostMapping("/updateRistoratoreDetails")
    public ResponseEntity<HttpStatus> UpdateRistoratoreDetails(@Valid @RequestBody UpdateRistoratoreRequest updateRistoratoreRequest, Authentication authentication){
        return dashboardPersonaleService.updateRistoratoreDetails(updateRistoratoreRequest, authentication.getName());
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<HttpStatus> UpdatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest, Authentication authentication){
        return dashboardPersonaleService.updatePassword(updatePasswordRequest, authentication.getName());
    }
}
