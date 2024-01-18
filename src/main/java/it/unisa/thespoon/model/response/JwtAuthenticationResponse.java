package it.unisa.thespoon.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe che rappresenta una response JWTAuthentication
 * @author Jacopo Gennaro Esposito
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    String token;
}