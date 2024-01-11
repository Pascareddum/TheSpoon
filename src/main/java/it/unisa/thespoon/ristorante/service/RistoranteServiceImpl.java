package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.InsertRistoranteRequest;
import it.unisa.thespoon.model.request.SearchRistoranteRequest;
import it.unisa.thespoon.model.request.UpdateRistoranteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Impmenta la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema ristorante, che gestice tutti gli aspetti inerenti i ristoranti
 * */
@Service
@RequiredArgsConstructor
public class RistoranteServiceImpl implements RistoranteService{

    private final RistoranteDAO ristoranteDAO;
    private final DashboardPersonaleService dashboardPersonaleService;

    /**
     * Metodo adibito all'inserimento di un nuovo ristorante
     *
     * @param insertRistoranteRequest Oggetto che rappresenta una richiesta di inserimento
     * @param email Email del ristoratore che effettua la richiesta
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     **/
    @Override
    public ResponseEntity<HttpStatus> insertRistorante(InsertRistoranteRequest insertRistoranteRequest, String email) {
        Ristoratore ristoratore = dashboardPersonaleService.getAllRistoratoreDetails(email);
        var ristorante = Ristorante
                .builder()
                .Nome(insertRistoranteRequest.getNome())
                .Via(insertRistoranteRequest.getVia())
                .N_Civico(insertRistoranteRequest.getN_Civico())
                .Cap(insertRistoranteRequest.getCap())
                .Provincia(insertRistoranteRequest.getProvincia())
                .Telefono(insertRistoranteRequest.getTelefono())
                .build();

        Ristorante newRistorante = ristoranteDAO.save(ristorante);
        ristoratore.getRistoranti().add(newRistorante);

        return dashboardPersonaleService.saveRistoratore(ristoratore);
    }

    /**
     * Metodo per aggiornare i dettagli di un ristorante
     *
     * @param updateRistoranteRequest rappresenta la richiesta di modifica dei dettagli del ristorante
     * @param idRistorante            Id univoco del ristorante di cui mondificare i dati
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     */
    @Override
    public ResponseEntity<HttpStatus> updateRistorante(UpdateRistoranteRequest updateRistoranteRequest, Integer idRistorante, String email) {
        Ristoratore ristoratore = dashboardPersonaleService.getAllRistoratoreDetails(email);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(idRistorante, ristoratore.getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(updateRistoranteRequest.getNome()!=null){
            ristorante.get().setNome(updateRistoranteRequest.getNome());
        }
        if(updateRistoranteRequest.getVia()!=null){
            ristorante.get().setVia(updateRistoranteRequest.getVia());
        }
        if(updateRistoranteRequest.getN_Civico()!=null){
            ristorante.get().setN_Civico(updateRistoranteRequest.getN_Civico());
        }
        if(updateRistoranteRequest.getProvincia()!=null){
            ristorante.get().setProvincia(updateRistoranteRequest.getProvincia());
        }
        if(updateRistoranteRequest.getCap()!=null){
            ristorante.get().setCap(updateRistoranteRequest.getCap());
        }
        if(updateRistoranteRequest.getTelefono()!=null){
            ristorante.get().setTelefono(updateRistoranteRequest.getTelefono());
        }

        ristoranteDAO.save(ristorante.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo per recuperare tutti i ristoranti associati ad un dato ristoratore
     *
     * @param idRistoratore rappresenta l'id del ristoratore per il quale si vuole recuperare la lista dei ristoranti
     * @return ResponseEntity<Set < Ristorante>> ResponseEntity contenente la lista dei ristoranti associati
     */
    @Override
    public ResponseEntity<Set<Ristorante>> getAllRistorantiByRistoratore(Integer idRistoratore) {
        Optional<Set<Ristorante>> ristoranti = ristoranteDAO.findRistoranteByOwners(idRistoratore);

        if(ristoranti.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ristoranti.get(), HttpStatus.OK);
    }

    /**
     * Firma del metodo per recuperare i dettagli di un ristorante
     *
     * @param idRistorante rappresenta l'id del ristorante per il quale si vuole recuperarne i dettagli
     * @return ResponseEntity<Ristorante> ResponseEntity contenente i dettagli del ristorante
     */
    @Override
    public ResponseEntity<Ristorante> getRistoranteByID(Integer idRistorante) {
        Optional<Ristorante> ristorante = ristoranteDAO.findById(idRistorante);

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ristorante.get(), HttpStatus.OK);
    }

    /**
     * Metodo per recuperare i ristoranti dato una serie di parametri in input
     *
     * @param searchRistoranteRequest rappresenta la richiesta di ricerca di un ristorante
     * @param nomeRistorante          nome del ristorante da cercare
     * @return ResponseEntity<Set < Ristorante>> ResponseEntity contenente la lista dei ristoranti trovati
     */
    @Override
    public ResponseEntity<Set<Ristorante>> searchRistorante(SearchRistoranteRequest searchRistoranteRequest, String nomeRistorante) {
        Optional<Set<Ristorante>> ristoranti = ristoranteDAO.findRistoranteByNomeAndVia(nomeRistorante, searchRistoranteRequest.getVia(),
                searchRistoranteRequest.getN_Civico(), searchRistoranteRequest.getProvincia(), searchRistoranteRequest.getCap());

        if(ristoranti.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ristoranti.get(), HttpStatus.OK);
    }


}
