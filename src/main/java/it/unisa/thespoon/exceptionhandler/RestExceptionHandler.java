package it.unisa.thespoon.exceptionhandler;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Classe che implementa il RestExceptionHandler, ovvero l'handler che gestisce tutte le eccezioni generate dalle
 * chiamate alle API
 *  @author Jacopo Gennaro Esposito
 * */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle handleHttpMessageNotReadable.
     * Gestisce gli errori di formattazione del JSON
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Handle NoHandlerFoundException.
     * Gestisce gli errori quando vengono contattati endpoint inesistenti
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            ApiError apiError = new ApiError(BAD_REQUEST);
            apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
            apiError.setDebugMessage(ex.getMessage());
            return buildResponseEntity(apiError);
    }

    /**
     * handleDataIntegrityViolationException, tratta errori generici del DB.
     *
     * @param ex DataIntegrityViolationException
     * @return oggetto ApiError
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }

        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * handleUserAlreadyExistException, gestisce l'errore causato da un'utente già
     * registrato che richiede di iscriversi
     *
     * @param ex UserAlreadyExistsException
     * @return oggetto ApiError
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistsException ex, WebRequest request){
        return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex.getCause()));
    }

    //Gestisce parametri mancanti nelle richieste

    /**
     * handleMethodArgumentNotValid utilizzato quando un oggetto fallisce la validazione di @Valid
     * @param ex      MethodArgumentNotValidException lanciata quando la validazione fallisce
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return oggetto ApiError
     * */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * handleUserAlreadyExistException, gestisce l'errore causato
     * dalla mancata corrispondenza fra il campo Password e rePassword
     *
     * @param ex PasswordDontMatchException
     * @return oggetto ApiError
     */
    @ExceptionHandler(PasswordDontMatchException.class)
    protected ResponseEntity<Object> handlePasswordDontMatchException(PasswordDontMatchException ex, WebRequest request){
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(), ex.getCause()));
    }

    @ExceptionHandler(InvalidAuthCredentials.class)
    protected ResponseEntity<Object> handleInvalidAuthCredentials(InvalidAuthCredentials ex, WebRequest request){
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getCause()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request){
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getCause()));
    }
}
