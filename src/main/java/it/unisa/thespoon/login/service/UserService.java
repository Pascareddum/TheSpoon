package it.unisa.thespoon.login.service;

import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa i metodi per recuperare i dettagli dell'utente e salvare l'utente nel DB
 * */
@Service
@RequiredArgsConstructor
public class UserService {

    private final RistoratoreDAO ristoratoreDAO;

    /**
     * Metodo per recuperare i dettagli di un'Utente
     * */
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return ristoratoreDAO.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    /**
     * Metodo per salvare un utente nel DB
     * */
    public Ristoratore save(Ristoratore newUser) {
        if (newUser.getId() == null) {

        }
        return ristoratoreDAO.save(newUser);
    }

}
