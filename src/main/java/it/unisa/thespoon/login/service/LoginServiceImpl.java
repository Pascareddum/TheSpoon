package it.unisa.thespoon.login.service;

import it.unisa.thespoon.exceptionhandler.InvalidAuthCredentials;
import it.unisa.thespoon.exceptionhandler.PasswordDontMatchException;
import it.unisa.thespoon.jwt.service.JwtService;
import it.unisa.thespoon.model.dao.RistoratoreDAO;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.entity.Role;
import it.unisa.thespoon.model.request.SignupRequest;
import it.unisa.thespoon.model.response.JwtAuthenticationResponse;
import it.unisa.thespoon.model.request.LoginRequest;

import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Jacopo Gennaro Esposito
 * Impmenta la classe che esplicita i metodi dell'interfaccia di Servizio per
 * il sottosistema di login, che gestice l'autenticazione
 * */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    /**
     * Si occupa delle operazioni crud per il ristoratore
     */
    private final RistoratoreDAO ristoratoreDAO;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Metodo adibito ad effettuare il login dell'utente registrato
     *
     * @param loginRequest Oggetto che rappresenta una richiesta di login
     * @return JwtAuthenticationResponse Token di autenticazioen
     */
    @SneakyThrows
    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var user = ristoratoreDAO.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidAuthCredentials("Invalid email or password.", new Throwable("Invalid email or password.")));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    /**
     * Metodo adibito ad effettuare la registrazione dell'utente
     *
     * @param signupRequest Oggetto che rappresenta una richiesta di registrazione
     * @return JwtAuthenticationResponse Token di autenticazioen
     */
    @SneakyThrows
    @Override
    public JwtAuthenticationResponse signUP(SignupRequest signupRequest) {
        if(!Objects.equals(signupRequest.getPassword(), signupRequest.getRePassword())){
            throw new PasswordDontMatchException("Le password inserite non corrispondono", new Throwable("Le password inserite non corrispondono"));
        }
        var user = Ristoratore
                .builder()
                .Nome(signupRequest.getNome())
                .Cognome(signupRequest.getCognome())
                .Email(signupRequest.getEmail())
                .Password(passwordEncoder.encode(signupRequest.getPassword()))
                .Telefono(signupRequest.getTelefono())
                .Data_Nascita(signupRequest.getData_Nascita())
                .role(Role.ROLE_RISTORATORE)
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


}
