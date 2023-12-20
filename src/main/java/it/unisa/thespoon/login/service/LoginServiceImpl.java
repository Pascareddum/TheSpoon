package it.unisa.thespoon.login.service;

import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    /**
     * Si occupa delle operazioni crud per il ristoratore
     */
    private final RistoratoreDAO ristoratoreDAO;

    public LoginServiceImpl(RistoratoreDAO ristoratoreDAO) {
        this.ristoratoreDAO = ristoratoreDAO;
    }

    /**
     * Metodo adibito ad effettuare il login dell'utente registrato
     * @param Email
     * @return
     */
    @Override
    public Ristoratore login(String Email, String Password) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        Ristoratore ristoratore = ristoratoreDAO.findByEmail(Email);
        //Implementare controllo
        return ristoratoreDAO.findByEmail(Email);
    }
}
