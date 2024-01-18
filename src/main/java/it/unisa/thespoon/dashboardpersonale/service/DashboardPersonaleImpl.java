package it.unisa.thespoon.dashboardpersonale.service;

import it.unisa.thespoon.exceptionhandler.PasswordDontMatchException;
import it.unisa.thespoon.model.dao.DashboardPersonaleDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.UpdatePasswordRequest;
import it.unisa.thespoon.model.request.UpdateRistoratoreRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementa la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema DashboardPersonale, che gestice l'area utente del ristoratore
 * @author Jacopo Gennaro Esposito
 * */
@Service
@RequiredArgsConstructor
public class DashboardPersonaleImpl implements DashboardPersonaleService{

    private final DashboardPersonaleDAO dashboardPersonaleDAO;
    private final PasswordEncoder passwordEncoder;

    /**
     * Metodo adibito ad ottennere i dettagli del ristoratore
     *
     * @param email Stringa della email associata al ristoratore
     * @return Ristoratore.RistoratoreDataDisplay Proiezione di Ristoratore contenente i dati da visualizzare
     */
    @Override
    public ResponseEntity<Ristoratore.RistoratoreDataDisplay> getRistoratoreDetails(String email) {
        Optional<Ristoratore.RistoratoreDataDisplay> ristoratore = dashboardPersonaleDAO.findDetailsByEmail(email);
        if(ristoratore.isEmpty())
            throw new UsernameNotFoundException("Account not registered at The Spoon");
        return new ResponseEntity<>(ristoratore.get(), HttpStatus.OK);
    }

    /**
     * Metodo adibito alla modifica dei dettagli del ristoratore
     *
     * @param updateRistoratoreRequest rappresenta la richiesta di modifica dei dati associati al profilo dell'utente
     * @param email Stringa della email associata al ristoratore
     * @return ResponseEntity Risposta HTTP
     */
    @Override
    public ResponseEntity<HttpStatus> updateRistoratoreDetails(UpdateRistoratoreRequest updateRistoratoreRequest, String email) {
        Optional<Ristoratore> ristoratore = dashboardPersonaleDAO.findAllDetailsByEmail(email);

        if(ristoratore.isEmpty())
            throw new UsernameNotFoundException("Account not registered at The Spoon");

        if(updateRistoratoreRequest.getNome()!=null){
            ristoratore.get().setNome(updateRistoratoreRequest.getNome());
        }
        if(updateRistoratoreRequest.getCognome()!=null){
            ristoratore.get().setCognome(updateRistoratoreRequest.getCognome());
        }
        if(updateRistoratoreRequest.getTelefono()!=null){
            ristoratore.get().setTelefono(updateRistoratoreRequest.getTelefono());
        }
        if(updateRistoratoreRequest.getData_Nascita()!=null){
            ristoratore.get().setData_Nascita(updateRistoratoreRequest.getData_Nascita());
        }
        if(updateRistoratoreRequest.getEmail()!=null){
            ristoratore.get().setEmail(updateRistoratoreRequest.getEmail());
        }

        dashboardPersonaleDAO.save(ristoratore.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo adibito alla modifica della password dell'account ristoratore
     *
     * @param updatePasswordRequest rappresenta la richiesta di modifica della password dell'account
     * @param email Stringa della email associata al ristoratore
     * @return ResponseEntity Risposta HTTP
     */
    @SneakyThrows
    @Override
    public ResponseEntity<HttpStatus> updatePassword(UpdatePasswordRequest updatePasswordRequest, String email) {
        Optional<Ristoratore> ristoratore = dashboardPersonaleDAO.findAllDetailsByEmail(email);

        if(ristoratore.isEmpty())
            throw new UsernameNotFoundException("Account not registered at The Spoon");

        if(!Objects.equals(updatePasswordRequest.getPassword(), updatePasswordRequest.getRePassword()))
            throw new PasswordDontMatchException("Le password inserite non corrispondono", new Throwable("Le password inserite non corrispondono"));

        ristoratore.get().setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        dashboardPersonaleDAO.save(ristoratore.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo adibito ad ottennere tutti i dettagli del ristoratore
     *
     * @param email Stringa della email associata al ristoratore
     * @return Ristoratore Entità ristoratore contenente i dettagli
     */
    @Override
    public Ristoratore getAllRistoratoreDetails(String email) {
        Optional<Ristoratore> ristoratore = dashboardPersonaleDAO.findAllDetailsByEmail(email);
        if(ristoratore.isEmpty())
            throw new UsernameNotFoundException("Account not registered at The Spoon");
        return ristoratore.get();
    }

    /**
     * Metodo adibito a salvare le modifiche apportate al ristoratore
     *
     * @param ristoratore Entità rappresentate il ristoratore
     * @return ResposeEntity codice di stato HTTP OK
     */
    @Override
    public ResponseEntity<HttpStatus> saveRistoratore(Ristoratore ristoratore) {
        dashboardPersonaleDAO.save(ristoratore);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
