package it.unisa.thespoon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    public Tavolo(String numeroTavolo, Byte stato, Integer capacità, Ristorante ristoranteProp) {
        NumeroTavolo = numeroTavolo;
        Stato = stato;
        Capacita = capacità;
        this.ristoranteProp = ristoranteProp;
    }

    public Tavolo() {

    }
}
