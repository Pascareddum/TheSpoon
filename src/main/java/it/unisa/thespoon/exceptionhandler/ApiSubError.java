package it.unisa.thespoon.exceptionhandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

abstract class ApiSubError {
}

/**
 *
 * Classe che estende ApiSubError e rappresenta gli erorri di validazione incontrati durante le chiamate REST
 * @author Jacopo Gennaro Esposito
 * */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError{
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
