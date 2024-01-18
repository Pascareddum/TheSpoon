package it.unisa.thespoon.exceptionhandler;

import lombok.NoArgsConstructor;

/**
 * Classe che implementa l'eccezione UserAlreadyExists
 * @author Jacopo Gennaro Esposito
 * */
@NoArgsConstructor
public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
