package it.unisa.thespoon.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author Jacopo Gennaro Esposito
 * Classe che rappresenta l'entit&agrave; ristoratore di TheSpoon
 * */
@Entity
@Builder
public class Ristoratore implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String Password;
    private String Nome;
    private String Cognome;
    private String Email;
    private String Telefono;
    private Date Data_Nascita;

    @Enumerated(EnumType.STRING)
    Role role;

    public Ristoratore(Integer Id, String Password, String Nome, String Cognome, String Email, String Telefono, Date Data_Nascita, Role role) {
        this.Id = Id;
        this.Password = Password;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Email = Email;
        this.Telefono = Telefono;
        this.Data_Nascita = Data_Nascita;
        this.role = role;
    }

    public Ristoratore() {

    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public String getEmail() {
        return Email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public Date getData_Nascita() {
        return Data_Nascita;
    }

    public String getPassword(){
        return Password;
    }

    public Role getRole(){return role;}

    @Override
    public String getUsername() {
        return Email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}