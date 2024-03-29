package it.unisa.thespoon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Classe che rappresenta l'entit&agrave; prodotto di TheSpoon
 * @author Jacopo Gennaro Esposito
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
    private BigDecimal Prezzo;
    private Integer idristorante;

    @ManyToMany(mappedBy = "prodottiMenu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Menu> Contained;

    @JsonIgnore
    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    List<ProdottoOrdine> ContainedOrders;

    public Prodotto(Integer id, String nome, String descrizione, BigDecimal prezzo, Set<Menu> contained) {
        Id = id;
        Nome = nome;
        Descrizione = descrizione;
        Prezzo = prezzo;
        Contained = contained;
    }

    public Prodotto(Integer id, String nome, String descrizione, BigDecimal prezzo, Set<Menu> contained, List<ProdottoOrdine> containedOrders) {
        Id = id;
        Nome = nome;
        Descrizione = descrizione;
        Prezzo = prezzo;
        Contained = contained;
        ContainedOrders = containedOrders;
    }

    public Prodotto(Integer id, String nome, String descrizione, BigDecimal prezzo, Integer idristorante, Set<Menu> contained, List<ProdottoOrdine> containedOrders) {
        Id = id;
        Nome = nome;
        Descrizione = descrizione;
        Prezzo = prezzo;
        this.idristorante = idristorante;
        Contained = contained;
        ContainedOrders = containedOrders;
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

    public BigDecimal getPrezzo() {
        return Prezzo;
    }

    public Set<Menu> getContained() {
        return Contained;
    }
}