package it.unisa.thespoon.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ProdottoOrdineID implements Serializable {
    @Column(name = "id_prodotto")
    private Integer idProdotto;

    @Column(name = "idordine")
    private Integer idOrdine;

}
