package it.unisa.thespoon.exceptionhandler;

import lombok.NoArgsConstructor;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa l'eccezione UserAlreadyExists
 * */
@NoArgsConstructor
public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
