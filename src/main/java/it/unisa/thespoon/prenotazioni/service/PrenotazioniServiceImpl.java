package it.unisa.thespoon.prenotazioni.service;

import it.unisa.thespoon.model.dao.PrenotazioneDAO;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.dao.TavoloDAO;
import it.unisa.thespoon.model.entity.Prenotazione;
import it.unisa.thespoon.model.entity.Ristorante;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Tavolo;
import it.unisa.thespoon.model.request.InsertPrenotazioneRequest;
import it.unisa.thespoon.model.request.UpdatePrenotazioneRequest;
import it.unisa.thespoon.model.response.PrenotazioneInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Classe che implementa i metodi del sottosistema Prenotazioni
 * @author Jacopo Gennaro Esposito
 */
@Service
@RequiredArgsConstructor
public class PrenotazioniServiceImpl implements PrenotazioniService {

    private final RistoranteDAO ristoranteDAO;
    private final RistoratoreDAO ristoratoreDAO;
    private final PrenotazioneDAO prenotazioniDAO;
    private final TavoloDAO tavoloDAO;

    @Autowired
    private PrenotazioneObserverService prenotazioneObserverService;

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    /**
     * Metodo adibito all'inserimento di una nuova prenotazione all'interno di TheSpoon
     * @param insertPrenotazioneRequest Oggetto che rappresenta una richiesta di inserimento di una nuova prenotazione
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    @Override
    public ResponseEntity<Prenotazione> insertPrenotazioen(InsertPrenotazioneRequest insertPrenotazioneRequest) {
        Optional<Ristorante> ristorante = ristoranteDAO.findById(insertPrenotazioneRequest.getIdRistorante());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Byte stato = 1;
        Integer capacitaTotale = 0;
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setPrenotazioneObserverService(prenotazioneObserverService);

        prenotazione.setOra(insertPrenotazioneRequest.getOra());
        prenotazione.setData(insertPrenotazioneRequest.getData());
        prenotazione.setEmail(insertPrenotazioneRequest.getEmail());
        prenotazione.setCellulare(insertPrenotazioneRequest.getTelefono());
        prenotazione.setChatId(insertPrenotazioneRequest.getChatID());
        prenotazione.setNr_Persone(insertPrenotazioneRequest.getNr_Persone());
        prenotazione.setPasswordprenotazione(generatePassword());
        prenotazione.setStato(Byte.valueOf("0"));
        prenotazione.setOwnerPrenotazione(ristorante.get());
        ristorante.get().getPrenotazioni().add(prenotazione);

        for(String tableID : insertPrenotazioneRequest.getTableIDs()){
            Optional<Tavolo> newTavolo = tavoloDAO.findByNumeroTavoloAndRistorantePropId(tableID, ristorante.get().getId());
            if(newTavolo.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Optional<Prenotazione> prenotazioneConcorrente = prenotazioniDAO.findPrenotazioneByDataAndOraAndTavoli(insertPrenotazioneRequest.getData(), insertPrenotazioneRequest.getOra(), newTavolo.get());
            if(prenotazioneConcorrente.isPresent())
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            capacitaTotale += newTavolo.get().getCapacita();
            prenotazione.getTavoli().add(newTavolo.get());
            newTavolo.get().getPrenotazioni().add(prenotazione);
        }

        if(!capacitaTotale.equals(insertPrenotazioneRequest.getNr_Persone()))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        prenotazioniDAO.save(prenotazione);
        prenotazione.setStato(stato, ristorante.get());


        return new ResponseEntity<>(prenotazione, HttpStatus.OK);
    }

    /**
     * Firma del metodo adibito alla modifica di una prenotazione all'interno di TheSpoon
     *
     * @param updatePrenotazioneRequest Oggetto che rappresenta una richiesta di modifica di una nuova prenotazione
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    @Override
    public ResponseEntity<Prenotazione> updatePrenotazione(UpdatePrenotazioneRequest updatePrenotazioneRequest) {
        Integer capacitaTotale = 0;
        Optional<Prenotazione> prenotazione = prenotazioniDAO.findById(updatePrenotazioneRequest.getIdPrenotazione());

        if(prenotazione.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        prenotazione.get().setPrenotazioneObserverService(prenotazioneObserverService);

        if(prenotazione.get().getStato()==2)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if(!updatePrenotazioneRequest.getPassword().equals(prenotazione.get().getPasswordprenotazione()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if(updatePrenotazioneRequest.getOra()!=null){
            prenotazione.get().setOra(updatePrenotazioneRequest.getOra());
        }

        if(updatePrenotazioneRequest.getData()!=null){
            prenotazione.get().setData(updatePrenotazioneRequest.getData());
        }

        if(updatePrenotazioneRequest.getNr_Persone()!=null){
            prenotazione.get().setNr_Persone(updatePrenotazioneRequest.getNr_Persone());
        }



        if(updatePrenotazioneRequest.getTableIDs()!=null){
            Set<Tavolo> oldTavoli = prenotazione.get().getTavoli();

            for (Tavolo oldTavolo : oldTavoli) {
                System.out.println(oldTavolo.getNumeroTavolo());
                oldTavolo.getPrenotazioni().remove(prenotazione.get());
            }

            for(String tableID : updatePrenotazioneRequest.getTableIDs()){
                Optional<Tavolo> newTavolo = tavoloDAO.findByNumeroTavoloAndRistorantePropId(tableID, prenotazione.get().getOwnerPrenotazione().getId());
                if(newTavolo.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);


                Optional<Prenotazione> prenotazioneConcorrente = prenotazioniDAO.findPrenotazioneByDataAndOraAndTavoliAndIdIsNot(updatePrenotazioneRequest.getData(), updatePrenotazioneRequest.getOra(), newTavolo.get(), updatePrenotazioneRequest.getIdPrenotazione());
                if(prenotazioneConcorrente.isPresent())
                    return new ResponseEntity<>(HttpStatus.CONFLICT);

                capacitaTotale += newTavolo.get().getCapacita();
                prenotazione.get().getTavoli().add(newTavolo.get());
                newTavolo.get().getPrenotazioni().add(prenotazione.get());
            }
        }

        if(!capacitaTotale.equals(updatePrenotazioneRequest.getNr_Persone()))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        prenotazione.get().setStato(Byte.valueOf("3"));
        prenotazioniDAO.save(prenotazione.get());
        prenotazione.get().setStato(Byte.valueOf("3"), prenotazione.get().getOwnerPrenotazione());

        return new ResponseEntity<>(prenotazione.get(), HttpStatus.OK);
    }

    /**
     * Firma del metodo per ottenere le prenotazioni di un ristorante dato il suo id
     *
     * @param idRistorante Identificativo del ristorante per il quale si intende recuperare la lista delle prenotazioni
     * @param Email        Email del ristoratore che effettua la richiesta
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    @Override
    public ResponseEntity<List<PrenotazioneInfo>> getAllPrenotazioni(Integer idRistorante, String Email) {
        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(Email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(idRistorante, ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<PrenotazioneInfo> prenotazioni = prenotazioniDAO.findPrenotazioneTavoloByIdRistorante(idRistorante);

        return new ResponseEntity<>(prenotazioni, HttpStatus.OK);
    }

    /**
     * Firma del metodo per ottenere i tavoli associati ad una prenotazione dato il suo id
     *
     * @param idPrenotazione Identificativo della prenotazione
     * @param Email          Email del ristoratore che effettua la richiesta
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    @Override
    public ResponseEntity<List<Tavolo>> getAllTavoliByIDPrenotazione(Integer idPrenotazione, String Email) {
        Optional<Prenotazione> prenotazione = prenotazioniDAO.findById(idPrenotazione);
        if(prenotazione.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(Email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(prenotazione.get().getOwnerPrenotazione().getId(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Tavolo> tavoli = prenotazioniDAO.findTavoliByIdPrenotazione(idPrenotazione);

        return new ResponseEntity<>(tavoli, HttpStatus.OK);
    }

    /**
     * Firma del metodo per confermare una prenotazione
     *
     * @param idPrenotazione Identificativo della prenotazione
     * @param Email          Email del ristoratore che effettua la richiesta
     * @return ResponseEntity Response contenete i dettagli della prenotazione
     */
    @Override
    public ResponseEntity<HttpStatus> confermaPrenotazione(Integer idPrenotazione, String Email) {
        Optional<Prenotazione> prenotazione = prenotazioniDAO.findById(idPrenotazione);
        if(prenotazione.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        prenotazione.get().setPrenotazioneObserverService(prenotazioneObserverService);

        Optional<Ristoratore> ristoratore = ristoratoreDAO.findByEmail(Email);
        if(ristoratore.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(prenotazione.get().getOwnerPrenotazione().getId(), ristoratore.get().getId());
        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        prenotazione.get().setStato(Byte.valueOf("2"));
        prenotazioniDAO.save(prenotazione.get());
        prenotazione.get().setStato(Byte.valueOf("2"), ristorante.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo per generare una password per le prenotazione, verrà usata in fase di modifica prenotazione
     * */
    private static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}
