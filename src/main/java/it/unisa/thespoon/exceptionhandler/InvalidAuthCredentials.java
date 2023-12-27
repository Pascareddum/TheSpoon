package it.unisa.thespoon.exceptionhandler;

public class InvalidAuthCredentials extends Exception{
    public InvalidAuthCredentials(String message, Throwable cause){ super(message, cause);}
}
