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

    //@Column(nullable = false)
    // private String testo;

    @ManyToOne
    @JoinColumn(name = "colloquio_id")
    private Colloquio colloquio;

    public Colloquio getColloquio() {
        return colloquio;
    }

    public void setColloquio(Colloquio colloquio) {
        this.colloquio = colloquio;
    }

   // @OneToOne(mappedBy = "domanda")
    //private Risposta risposta;
   @OneToOne(mappedBy = "domanda", cascade = CascadeType.ALL)
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


    public void setRispostaGpt(String rispostaGpt){
        this.risposta.setTestoValutazioneGpt(rispostaGpt);
    }
}