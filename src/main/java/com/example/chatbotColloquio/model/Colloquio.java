package com.example.chatbotColloquio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "colloquio")
public class Colloquio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orario;

    @Column(nullable = false)
    private Integer difficolta;

    @Column(nullable = false)
    private String argomentoColloquio;

    @JsonManagedReference
    @OneToMany(mappedBy = "colloquio",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Domanda> domande = new ArrayList<>() ;


    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


    public Colloquio(Long id, LocalDateTime orario, Integer difficolta, String argomentoColloquio) {
        this.id = id;
        this.orario = orario;
        this.difficolta = difficolta;
        this.argomentoColloquio = argomentoColloquio;
        //this.domande = new ArrayList<>();
    }


    public Colloquio(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrario() {
        return orario;
    }

    public void setOrario(LocalDateTime orario) {
        this.orario = orario;
    }

    @JsonProperty("domande")
    public List<Domanda> getDomandaList() {
        return this.domande;
    }


    public void setDomande(Domanda domanda) {
        this.domande.add(domanda) ;
    }

    public void setUtente(Utente utente) {
        this.utente=utente;
    }

    public void setArgomentoColloquio(String argomentoColloquio) {
        this.argomentoColloquio = argomentoColloquio;
    }

    public void setDifficolta(int difficolta) {
        this.difficolta=difficolta;
    }

    public String getArgomentoColloquio() {
        return this.argomentoColloquio;
    }
}