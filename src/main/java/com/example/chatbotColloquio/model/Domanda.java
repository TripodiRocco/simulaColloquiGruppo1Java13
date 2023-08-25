package com.example.chatbotColloquio.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "domanda")
public class Domanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String testoDomanda;

    @ManyToOne
    @JoinColumn(name = "colloquio_id")
    private Colloquio colloquio;

    @OneToOne(mappedBy = "domanda")
    //private List<Risposta> risposte;
    private Risposta risposta;
    public Domanda(Long id, String testoDomanda) {
        this.id = id;
        this.testoDomanda = testoDomanda;
    }

    public Domanda(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestoDomanda() {
        return testoDomanda;
    }

    public void setTestoDomanda(String testoDomanda) {
        this.testoDomanda = testoDomanda;
    }

    public void setRisposta(Risposta rispostaUtente) {
        this.risposta=rispostaUtente;
    }
}