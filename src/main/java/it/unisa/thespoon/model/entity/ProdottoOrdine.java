package it.unisa.thespoon.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;

import java.util.Objects;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che rappresenta l'entit&agrave; ProdottoOrdine di TheSpoon
 * */
@Entity
@Setter
@Getter
@Builder
public class ProdottoOrdine {
    @EmbeddedId
    ProdottoOrdineID id;

    @MapsId("idOrdine")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idordine")
    private Ordine ordine;

    @MapsId("idProdotto")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prodotto")
    private Prodotto prodotto;

    public ProdottoOrdine() {

    }

    public ProdottoOrdine(ProdottoOrdineID id, Ordine ordine, Prodotto prodotto) {
        this.id = id;
        this.ordine = ordine;
        this.prodotto = prodotto;
    }
}
