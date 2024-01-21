package it.unisa.thespoon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe che rappresenta l'entit&agrave; Tavolo di TheSpoon
 * @author Jacopo Gennaro Esposito
 * */
@Setter
@Getter
@Entity
@Builder
public class Tavolo {

    @Id
    @Column(name = "numerotavolo")
    String NumeroTavolo;

    Byte Stato;

    Integer Capacita;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_ristorante")
    private Ristorante ristoranteProp;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "prenotazionetavolo",
            joinColumns = @JoinColumn(name = "numerotavolo"),
            inverseJoinColumns = @JoinColumn(name = "idprenotazione")
    )
    private Set<Prenotazione> prenotazioni = new HashSet<>();

    public Tavolo(String numeroTavolo, Byte stato, Integer capacità, Ristorante ristoranteProp) {
        NumeroTavolo = numeroTavolo;
        Stato = stato;
        Capacita = capacità;
        this.ristoranteProp = ristoranteProp;
    }

    public Tavolo(String numeroTavolo, Byte stato, Integer capacita, Ristorante ristoranteProp, Set<Prenotazione> prenotazioni) {
        NumeroTavolo = numeroTavolo;
        Stato = stato;
        Capacita = capacita;
        this.ristoranteProp = ristoranteProp;
        this.prenotazioni = prenotazioni;
    }

    public Tavolo() {

    }
}
