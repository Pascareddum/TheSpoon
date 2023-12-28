package it.unisa.thespoon.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che implementa il "servizio" per l'autenticazione via JWT
 * */
@Service
public class JwtService {

    /** Setto la secretKey usata per "firmare" il token*/
    @Value("${token.secret.key}")
    String jwtSecretKey;

    /** Setto la scadenza del token*/
    @Value("${token.expirationms}")
    Long jwtExpirationMS;

    /**
     * Metodo per estrarre l'Username dal token
     * @param token Token di autenticazione
     * @return String UserName estratto dal Token
     * */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Metodo per generare il token
     * @param userDetails dettagli dell'utente
     * @return String Token generato
     * */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Metodo per la verifica della validità del token
     * @param token Token di autenticazione
     * @param userDetails dettagli dell'utente
     * @return boolean
     * */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Metodo per estrarre i claims dal Token
     * @param token Token di autenticazione
     * @param claimsResolvers claimsEstratti
     * @return boolean
     * */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Metodo per generare il token
     * @param extraClaims Claims
     * @param userDetails Dettagli dell'utente
     * @return String token di autenticazione
     * */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Metodo per verificare se il token è scaduto
     * @param token Token di autenticazione
     * @return boolean
     * */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
