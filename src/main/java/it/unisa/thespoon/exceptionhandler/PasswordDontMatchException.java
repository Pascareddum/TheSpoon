package it.unisa.thespoon.exceptionhandler;

/**
 * Classe che implementa l'eccezione PasswordDontMatchException
 * @author Jacopo Gennaro Esposito
 * */
public class PasswordDontMatchException extends Exception {
    public PasswordDontMatchException(String message, Throwable cause){ super(message, cause); }
}
