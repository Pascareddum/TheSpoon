package it.unisa.thespoon.ristorante.service;

import it.unisa.thespoon.dashboardpersonale.service.DashboardPersonaleService;
import it.unisa.thespoon.model.dao.MenuDAO;
import it.unisa.thespoon.model.dao.RistoranteDAO;
import it.unisa.thespoon.model.dao.TavoloDAO;
import it.unisa.thespoon.model.entity.*;
import it.unisa.thespoon.model.request.*;
import it.unisa.thespoon.prodotto.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Impmenta la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema ristorante, che gestice tutti gli aspetti inerenti i ristoranti
 * @author Jacopo Gennaro Esposito
 * */
@Service
@RequiredArgsConstructor
public class RistoranteServiceImpl implements RistoranteService{

    private final RistoranteDAO ristoranteDAO;
    private final MenuDAO menuDAO;
    private final TavoloDAO tavoloDAO;
    private final DashboardPersonaleService dashboardPersonaleService;
    private final ProdottoService prodottoService;

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
     * @return ResponseEntity ResponseEntity contenente la lista dei ristoranti associati
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
     * @return ResponseEntity ResponseEntity contenente i dettagli del ristorante
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
     * @return ResponseEntity ResponseEntity contenente la lista dei ristoranti trovati
     */
    @Override
    public ResponseEntity<Set<Ristorante>> searchRistorante(SearchRistoranteRequest searchRistoranteRequest, String nomeRistorante) {
        Optional<Set<Ristorante>> ristoranti = ristoranteDAO.findRistoranteByNomeAndVia(nomeRistorante, searchRistoranteRequest.getVia(),
                searchRistoranteRequest.getN_Civico(), searchRistoranteRequest.getProvincia(), searchRistoranteRequest.getCap());

        if(ristoranti.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ristoranti.get(), HttpStatus.OK);
    }

    /**
     * Metodo per aggiungere un menu ad un ristorante
     *
     * @param insertMenuRequest rappresenta la richiesta di inserimento di un menu
     * @param email Email del ristoratore
     * @return ResponseEntity codice di risposta HTTP
     */
    @Override
    public ResponseEntity<HttpStatus> insertMenu(InsertMenuRequest insertMenuRequest, String email) {
        Ristoratore ristoratore = dashboardPersonaleService.getAllRistoratoreDetails(email);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(insertMenuRequest.getIdRistorante(), ristoratore.getId());

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var menu = Menu
                .builder()
                //.fk_id_ristorante(ristorante.get().getId())
                .Categoria(insertMenuRequest.getDescrizione())
                .Nome(insertMenuRequest.getNome())
                .build();

        menu.setRistorante(ristorante.get());
        Menu newMenu = menuDAO.save(menu);
        ristorante.get().getMenus().add(newMenu);

        return dashboardPersonaleService.saveRistoratore(ristoratore);
    }

    /**
     * Metodo per aggiungere un prodotto ad un menu
     *
     * @param idMenu Identificativo del menu per il quale si intende aggiungere un prodotto
     * @param idProdotto Identificativo del prodotto da aggiungere al menu
     * @param email Email del ristoratore
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     */
    @Override
    public ResponseEntity<HttpStatus> addProductToMenu(Integer idMenu, Integer idProdotto, Integer idRistorante, String email) {
        Optional<Menu> menu = menuDAO.findById(idMenu);
        Optional<Prodotto> newProdotto = prodottoService.getProdotto(idProdotto);

        if(menu.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(newProdotto.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Ristoratore ristoratore = dashboardPersonaleService.getAllRistoratoreDetails(email);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(idRistorante, ristoratore.getId());

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        if(menu.get().getRistorante().getId() != ristorante.get().getId())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        menu.get().getProdottiMenu().add(newProdotto.get());
        menuDAO.save(menu.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo per rimuovere un prodotto ad un menu
     *
     * @param idMenu       ID del menu per il quale si intende rimuovere un prodotto
     * @param idProdotto   ID del prodotto da rimuovere
     * @param idRistorante ID del ristorante
     * @return HttpStatusCode ResponseEntity Codice di stato http
     */
    @Override
    public ResponseEntity<HttpStatus> removeProductFromMenu(Integer idMenu, Integer idProdotto, Integer idRistorante, String email) {
        Optional<Menu> menu = menuDAO.findById(idMenu);
        Optional<Prodotto> removeProdotto = prodottoService.getProdotto(idProdotto);

        if(menu.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(removeProdotto.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Ristoratore ristoratore = dashboardPersonaleService.getAllRistoratoreDetails(email);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(idRistorante, ristoratore.getId());

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        if(menu.get().getRistorante().getId() != ristorante.get().getId())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        menu.get().getProdottiMenu().remove(removeProdotto.get());
        menuDAO.save(menu.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo per ottenere i menu associati ad un ristorante
     *
     * @param ID Id del ristorante per il quale si vuole recuperare la lista dei menu
     * @return ResponseEntity ResponseEntity contenente la lista dei menu associati
     */
    @Override
    public ResponseEntity<Set<Menu>> getMenusByRistoranteID(Integer ID) {
        Optional<Set<Menu>> menus = menuDAO.findMenusByRistoranteId(ID);

        if(menus.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(menus.get(), HttpStatus.OK);
    }

    /**
     * Metodo per ottenere il menu associato ad un dato id
     *
     * @param idMenu Id del ristorante per il quale si vuole recuperare la lista dei menu
     * @return ResponseEntity ResponseEntity contenente la lista dei menu associati
     */
    @Override
    public ResponseEntity<Menu> getMenusByID(Integer idMenu) {
        Optional<Menu> menu = menuDAO.findById(idMenu);

        if(menu.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(menu.get(), HttpStatus.OK);
    }

    /**
     * Metodo per ottenere i prodotti associati ad un dato menu
     *
     * @param idMenu Id del menu per il quale si vuole recuperare la lista dei prodotti
     * @return ResponseEntity ResponseEntity contenente la lista dei prodotti associati
     */
    @Override
    public ResponseEntity<Set<Prodotto>> getProdottiByMenuID(Integer idMenu) {
        Optional<Menu> menu = menuDAO.findById(idMenu);

        if(menu.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(menu.get().getProdottiMenu(), HttpStatus.OK);
    }

    /**
     * Metodo per inserire un nuovo tavolo
     *
     * @param insertTavoloRequest Oggetto che rappresenta una richiesta di inserimento
     * @param email               Email del ristoratore che effettua la richiesta
     * @return ResponseEntity HttpStatus Codice di stato HTTP
     **/
    @Override
    public ResponseEntity<HttpStatus> insertTavolo(InsertTavoloRequest insertTavoloRequest, String email) {
        Ristoratore ristoratore = dashboardPersonaleService.getAllRistoratoreDetails(email);

        Optional<Ristorante> ristorante = ristoranteDAO.findByIdAndAndOwnersID(insertTavoloRequest.getIdRistorante(), ristoratore.getId());

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var tavolo = Tavolo
                .builder()
                .NumeroTavolo(insertTavoloRequest.getNumeroTavolo())
                .Stato(insertTavoloRequest.getStato())
                .Capacita(insertTavoloRequest.getCapacita())
                .build();

        tavolo.setRistoranteProp(ristorante.get());
        tavoloDAO.save(tavolo);
        ristorante.get().getTables().add(tavolo);
        ristoranteDAO.save(ristorante.get());


        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Metodo per recuperare i tavoli associati ad un ristorante
     *
     * @param idRistorante ID del ristorante per il quale si vuole ottenere la lista dei tavoli
     * @return ResponseEntity Set contenente la lista dei tavoli
     **/
    @Override
    public ResponseEntity<Set<Tavolo>> getTavoliRistorante(Integer idRistorante) {
        Optional<Ristorante> ristorante = ristoranteDAO.findById(idRistorante);

        if(ristorante.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<Set<Tavolo>> tavoli = tavoloDAO.findByRistorantePropId(idRistorante);

        if(tavoli.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tavoli.get(), HttpStatus.OK);
    }

    /**
     * Metodo per recuperare i dettagli di un tavolo dato il suo ID
     *
     * @param idTavolo     ID del ristorante per il quale si vuole ottenere la lista dei tavoli
     * @param idRistorante ID del ristorante
     * @return ResponseEntity Response Entity contenente i dettagli del tavolo
     **/
    @Override
    public ResponseEntity<Tavolo> getTavoloByID(String idTavolo, Integer idRistorante) {
        Optional<Tavolo> tavolo = tavoloDAO.findByNumeroTavoloAndRistorantePropId(idTavolo, idRistorante);

        if(tavolo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tavolo.get(), HttpStatus.OK);
    }


}
