package com.example.chatbotColloquio.model;

import jakarta.persistence.*;


@Entity
@Table (name = "risposta")
public class Risposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String testoRisposta;

    @Column(nullable = false)
    private String testoValutazioneGpt;


    @Column(nullable = false)
    private Integer punteggio; //serve a salvare la valutazione della risposta


   // @ManyToOne
   // @JoinColumn(name = "domanda_id")
   // private Domanda domanda;

    @OneToOne
    @JoinColumn(name = "domanda_id")
    private Domanda domanda;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public Risposta(String testoRisposta){
        this.testoRisposta = testoRisposta;
    }
    public Risposta(Long id, String testoRisposta, Integer punteggio) {
        this.id = id;
        this.testoRisposta = testoRisposta;
        this.punteggio = punteggio;
    }

    public Risposta(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestoRisposta() {
        return testoRisposta;
    }

    public void setTestoRisposta(String testoRisposta) {
        this.testoRisposta = testoRisposta;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        this.punteggio = punteggio;
    }

    public String getTestoValutazioneGpt() {
        return testoValutazioneGpt;
    }

    public void setTestoValutazioneGpt(String testoValutazioneGpt) {
        this.testoValutazioneGpt = testoValutazioneGpt;
    }

    public Domanda getDomanda() {
        return domanda;
    }

    public void setDomanda(Domanda domanda) {
        this.domanda = domanda;
    }
}
