package it.unisa.thespoon.exceptionhandler;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa l'eccezione PasswordDontMatchException
 * */
public class PasswordDontMatchException extends Exception {
    public PasswordDontMatchException(String message, Throwable cause){ super(message, cause); }
}
