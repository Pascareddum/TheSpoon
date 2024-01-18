package it.unisa.thespoon.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe che rappresenta un errore generato da una chiamata alle API
 * @author Jacopo Gennaro Esposito
 * */
@Setter
@Getter
public class ApiError {
    /** Status contiene il codice della chiamata HTTP, come per esempio HTTP 400 BAD Request*/
    private HttpStatus status;
    /** Contiene le info su data e ora di quando è avvenuto l'errore*/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    /** Contiene un messaggio user-friendly per descrivere l'errore */
    private String message;
    /** Contiene un messaggio d'errore più dettagliato */
    private String debugMessage;
    /** Lista contenente gli altri sotto-errori in caso di errori in cascata */
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    /**
     * addSubError metodo per aggiungere dei "sotto-errori" all'eccezione principale, per esempio per
     * specificare quali campi di un JSON non sono stati compilati
     * */
    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    /**
     * addValidationError metodo usato per aggiungere un errore di validazione
     * @param object Oggetto che ha fallito il controllo
     * @param field Campo interessato
     * @param rejectedValue Il valore che è stato considerato non valido
     * @param message Messaggio di errore
     * */
    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    /**
     * addValidationError metodo usato per aggiungere un errore di validazione
     * @param object Oggetto che ha fallito il controllo
     * @param message Messaggio di errore
     * */
    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    /**
     * addValidationError metodo usato per aggiungere un errore di validazione a partire da un oggetto FieldError
     * @param fieldError
     * */
    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Metodo utilizzato per aggiungere errori di ConstraintViolation, quando @Validated fallisce
     * @param cv ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}
