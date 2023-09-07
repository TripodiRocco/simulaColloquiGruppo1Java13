package com.example.chatbotColloquio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table (name = "domanda")
public class Domanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String testoDomanda;

    //@OneToOne
    //@JoinColumn(name = "risposta_id")
    //private Risposta risposta;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "risposta_id")
    private Risposta risposta;



    //@Column(nullable = false)
    // private String testo;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "colloquio_id")
    private Colloquio colloquio;

    public Colloquio getColloquio() {
        return colloquio;
    }

    public void setColloquio(Colloquio colloquio) {
        this.colloquio = colloquio;
    }


    public Domanda(String testoDomanda) {
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


/*
    public void setRispostaGpt(String rispostaGpt){
       this.risposta.setTestoValutazioneGpt(rispostaGpt);
    }

    public void setRispostaUtente(String rispostaUtente){
        this.risposta.setTestoRisposta(rispostaUtente);
    }

*/

    public Risposta getRisposta() {
        return risposta;
    }

    public void setRisposta(Risposta risposta) {
        this.risposta = risposta;
    }
}