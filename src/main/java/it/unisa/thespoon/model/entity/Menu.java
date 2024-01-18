package it.unisa.thespoon.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Classe che rappresenta l'entit&agrave; Menu di TheSpoon
 * @author Jacopo Gennaro Esposito
 * */
@Setter
@Getter
@Entity
@Builder
public class Menu {

    @Id
    @Column(name = "IdMenu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String Nome;
    private String Categoria;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "menuprodotto",
            joinColumns = @JoinColumn(name = "IdMenu"),
            inverseJoinColumns = @JoinColumn(name = "id_prodotto")
    )
    Set<Prodotto> prodottiMenu;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fk_id_ristorante")
    private Ristorante ristorante;



    public Menu(Integer id, String nome, String categoria, Set<Prodotto> prodottiMenu, Ristorante ristorante) {
        Id = id;
        Nome = nome;
        Categoria = categoria;
        this.prodottiMenu = prodottiMenu;
    }

    public Menu() {

    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public String getCategoria() {
        return Categoria;
    }

    public Set<Prodotto> getProdottiMenu() {
        return prodottiMenu;
    }
}
