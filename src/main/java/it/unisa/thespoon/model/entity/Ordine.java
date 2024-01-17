package it.unisa.thespoon.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che rappresenta l'entit&agrave; ordini di TheSpoon
 * */

@Setter
@Getter
@Entity
@Builder
public class Ordine {

    @Column(name = "idordine")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idordine;
    Integer Idristorante;
    Byte Tipologia;
    Time Ora;
    String Nr_Tavolo;
    Integer Quantita;
    @Column(name = "chatid")
    Integer ChatId;
    Byte Stato;
    BigDecimal Totale;

    /*
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "prodotto_ordine",
            joinColumns = @JoinColumn(name = "idordine"),
            inverseJoinColumns = @JoinColumn(name = "id_prodotto")
    )
    List<Prodotto> products;*/


    @JsonIgnore
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<ProdottoOrdine> products;

    public Ordine(Integer idordine, Integer idristorante, Byte tipologia, Time ora, String nr_Tavolo, Integer quantita, Integer chatId, Byte stato, BigDecimal totale, List<ProdottoOrdine> products) {
        this.idordine = idordine;
        Idristorante = idristorante;
        Tipologia = tipologia;
        Ora = ora;
        Nr_Tavolo = nr_Tavolo;
        Quantita = quantita;
        ChatId = chatId;
        Stato = stato;
        Totale = totale;
        this.products = products;
    }

    public Ordine() {
        products = new ArrayList<>();
    }

}
