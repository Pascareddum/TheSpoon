package it.unisa.thespoon.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unisa.thespoon.ordini.service.OrdineObserverService;
import it.unisa.thespoon.prenotazioni.service.PrenotazioneObserverService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che rappresenta l'entit&agrave; prenotazione di TheSpoon
 * @author Jacopo Gennaro Esposito
 * */

@Setter
@Getter
@Entity
@Builder
public class Prenotazione {

    @Column(name = "idprenotazione")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private LocalDate data;
    private Time ora;
    private Integer Nr_Persone;
    private String Email;
    private String Cellulare;
    @Column(name = "chatid")
    private Integer ChatId;
    private Byte Stato;
    private String Passwordprenotazione;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idristorante")
    private Ristorante ownerPrenotazione;

    @JsonIgnore
    @ManyToMany(mappedBy = "prenotazioni")
    private Set<Tavolo> tavoli = new HashSet<>();

    @Transient // Evita la persistenza di questo campo
    @JsonIgnore
    private PrenotazioneObserverService prenotazioneObserverService;

    public Prenotazione(Integer id, LocalDate data, Time ora, Integer nr_Persone, String email, String cellulare, Integer chatId, Byte stato, String passwordprenotazione, Ristorante ownerPrenotazione, Set<Tavolo> tavoli) {
        Id = id;
        this.data = data;
        this.ora = ora;
        Nr_Persone = nr_Persone;
        Email = email;
        Cellulare = cellulare;
        ChatId = chatId;
        Stato = stato;
        Passwordprenotazione = passwordprenotazione;
        this.ownerPrenotazione = ownerPrenotazione;
        this.tavoli = tavoli;
    }

    public Prenotazione(Integer id, LocalDate data, Time ora, Integer nr_Persone, String email, String cellulare, Integer chatId, Byte stato, String passwordprenotazione, Ristorante ownerPrenotazione, Set<Tavolo> tavoli, PrenotazioneObserverService prenotazioneObserverService) {
        Id = id;
        this.data = data;
        this.ora = ora;
        Nr_Persone = nr_Persone;
        Email = email;
        Cellulare = cellulare;
        ChatId = chatId;
        Stato = stato;
        Passwordprenotazione = passwordprenotazione;
        this.ownerPrenotazione = ownerPrenotazione;
        this.tavoli = tavoli;
        this.prenotazioneObserverService = prenotazioneObserverService;
    }

    public void setStato(Byte stato, Ristorante ristorante) {
        Stato = stato;
        if (prenotazioneObserverService != null) {
            prenotazioneObserverService.notifyObservers(this, ristorante);
        }
    }

    public Prenotazione() {
    }
}
