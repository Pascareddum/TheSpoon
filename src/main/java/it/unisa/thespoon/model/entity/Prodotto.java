package it.unisa.thespoon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che rappresenta l'entit&agrave; prodotto di TheSpoon
 * */

@Setter
@Getter
@Entity
@Builder
public class Prodotto {
    @Column(name = "id_prodotto")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String Nome;
    private String Descrizione;
    private Float Prezzo;

    @ManyToMany(mappedBy = "prodottiMenu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Menu> Contained;

    public Prodotto(Integer id, String nome, String descrizione, Float prezzo, Set<Menu> contained) {
        Id = id;
        Nome = nome;
        Descrizione = descrizione;
        Prezzo = prezzo;
        Contained = contained;
    }

    public Prodotto() {

    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public Float getPrezzo() {
        return Prezzo;
    }

    public Set<Menu> getContained() {
        return Contained;
    }
}
