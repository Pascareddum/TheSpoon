package it.unisa.thespoon.login.service;

import it.unisa.thespoon.exceptionhandler.UserAlreadyExistsException;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristoratore;

import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe che implementa i metodi per recuperare i dettagli dell'utente e salvare l'utente nel DB
 * @author Jacopo Gennaro Esposito
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
    @SneakyThrows
    public Ristoratore save(Ristoratore newUser) {
        Optional<Ristoratore> r = ristoratoreDAO.findByEmail(newUser.getEmail());
        if (r.isPresent()) {
            throw new UserAlreadyExistsException("Account already registered to TheSpoon", new Throwable("Account already registered to TheSpoon"));
        }
        return ristoratoreDAO.save(newUser);
    }

}
