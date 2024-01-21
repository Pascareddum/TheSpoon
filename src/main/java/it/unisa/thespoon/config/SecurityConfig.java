

package it.unisa.thespoon.config;

import it.unisa.thespoon.filters.JwtAuthenticationFilter;
import it.unisa.thespoon.login.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuration File per Spring Security, framework spring per gestire l'accesso alle API
 * @author Jacopo Gennaro Esposito
 * */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf
                        .disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/dashboard/ristoratoreDetails").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.POST, "/dashboard/**").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.POST, "/ristorante/insertRistorante").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.GET, "/ristorante/restaurantsList/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ristorante/getRistorante/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ristorante/updateRistorante/**").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.POST, "/ristorante/insertMenu").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.POST, "/ristorante/addProductToMenu/**").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.DELETE, "/ristorante/removeProductMenu/**").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.GET, "/ristorante/getMenuByIDRistorante/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ristorante/getMenuByID/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ristorante/getProdottiByIDMenu/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ristorante/insertTavolo").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.GET, "/ristorante/getTavoliRistorante/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ristorante/getTavoloById/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "prodotto/**").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.DELETE, "prodotto/removeProdotto/**").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.GET, "prodotto/getProdotto/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "prodotto/getAllProdottiByIdRistorante/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ristorante/ricercaRistorante/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ordini/insertOrdine").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ordini/confermaOrdine/").hasRole("RISTORATORE")
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pagamenti/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/prenotazioni/insertPrenotazione").permitAll()
                        .requestMatchers(HttpMethod.POST, "/prenotazioni/updatePrenotazione").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

