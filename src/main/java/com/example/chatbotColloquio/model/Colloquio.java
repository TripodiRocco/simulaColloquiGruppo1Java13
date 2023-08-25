package com.example.chatbotColloquio.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


    @OneToMany(mappedBy = "colloquio")
    private List<Domanda> domande;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


    public Colloquio(Long id, LocalDateTime orario, Integer difficolta, String argomentoColloquio) {
        this.id = id;
        this.orario = orario;
        this.difficolta = difficolta;
        this.argomentoColloquio = argomentoColloquio;
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

}