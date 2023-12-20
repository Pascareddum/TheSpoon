package it.unisa.thespoon.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Ristoratore {
    @Id
    private Integer Id;
    private String Password;
    private String Nome;
    public String Cognome;
    private String Email;
    private String Telefono;
    private Date Data_Nascita;

    public Ristoratore(Integer Id, String Password, String Nome, String Cognome, String Email, String Telefono, Date Data_Nascita) {
        this.Id = Id;
        this.Password = Password;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Email = Email;
        this.Telefono = Telefono;
        this.Data_Nascita = Data_Nascita;
    }

    public Ristoratore() {

    }
}
