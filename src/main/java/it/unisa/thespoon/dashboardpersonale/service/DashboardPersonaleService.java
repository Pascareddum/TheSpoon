package it.unisa.thespoon.dashboardpersonale.service;


import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.UpdatePasswordRequest;
import it.unisa.thespoon.model.request.UpdateRistoratoreRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 Interfaccia per i metodi del sottosistema DashboardPersonale
 @author Jacopo Gennaro Esposito
 **/
public interface DashboardPersonaleService {

    ResponseEntity<Ristoratore.RistoratoreDataDisplay> getRistoratoreDetails(String email);

    ResponseEntity<HttpStatus> updateRistoratoreDetails(UpdateRistoratoreRequest updateRistoratoreRequest, String email);

    ResponseEntity<HttpStatus> updatePassword(UpdatePasswordRequest updatePasswordRequest, String email);

    Ristoratore getAllRistoratoreDetails(String email);

    ResponseEntity<HttpStatus> saveRistoratore(Ristoratore ristoratore);

}
