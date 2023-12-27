package it.unisa.thespoon.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacopo Gennato Esposito
 * Classe che rappresenta una response JWTAuthentication
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    String token;
}