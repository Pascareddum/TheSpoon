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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdottoOrdineID)) return false;
        ProdottoOrdineID that = (ProdottoOrdineID) o;
        return Objects.equals(getIdProdotto(), that.getIdProdotto()) &&
                Objects.equals(getIdOrdine(), that.getIdOrdine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdProdotto(), getIdOrdine());
    }

}
