package it.unisa.thespoon.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Jacopo Gennaro Esposito
 * Classe che rappresenta l'entit&agrave; ristorante di TheSpoon
 * */
@Setter
@Getter
@Entity
@Builder
public class Ristorante {

    @Column(name = "id_ristorante")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String Nome;
    private String N_Civico;
    private Integer Cap;
    private String Via;
    private String Provincia;
    private String Telefono;

    @ManyToMany(mappedBy = "ristoranti", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Ristoratore> Owners;

    @OneToMany(mappedBy = "ristorante", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Menu> Menus;

    @OneToMany(mappedBy = "ristoranteProp", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Tavolo> Tables;



    public Ristorante(Integer id, String nome, String n_Civico, Integer cap, String via, String provincia, String telefono,
                      Set<Ristoratore> owners) {
        Id = id;
        Nome = nome;
        N_Civico = n_Civico;
        Cap = cap;
        Via = via;
        Provincia = provincia;
        Telefono = telefono;
        Owners = owners;
    }

    public Ristorante(Integer id, String nome, String n_Civico, Integer cap, String via, String provincia, String telefono, Set<Ristoratore> owners, Set<Menu> menus) {
        Id = id;
        Nome = nome;
        N_Civico = n_Civico;
        Cap = cap;
        Via = via;
        Provincia = provincia;
        Telefono = telefono;
        Owners = owners;
        Menus = menus;
    }

    public Ristorante(Integer id, String nome, String n_Civico, Integer cap, String via, String provincia, String telefono, Set<Ristoratore> owners, Set<Menu> menus, Set<Tavolo> tables) {
        Id = id;
        Nome = nome;
        N_Civico = n_Civico;
        Cap = cap;
        Via = via;
        Provincia = provincia;
        Telefono = telefono;
        Owners = owners;
        Menus = menus;
        Tables = tables;
    }

    public Ristorante(){

    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public String getN_Civico() {
        return N_Civico;
    }

    public Integer getCap() {
        return Cap;
    }

    public String getVia() {
        return Via;
    }

    public String getProvincia() {
        return Provincia;
    }

    public String getTelefono() {
        return Telefono;
    }
}

