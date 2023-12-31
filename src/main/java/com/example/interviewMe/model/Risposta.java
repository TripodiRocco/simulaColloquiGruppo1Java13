package com.example.interviewMe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table (name = "risposta")
public class Risposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 1023)
    private String testoRisposta;

    @NotNull
    @Column(nullable = false, length =  1023)
    private String testoValutazioneGpt;

    @NotNull
    @Column(nullable = false)
    private Integer punteggio; //serve a salvare la valutazione della risposta


   // @ManyToOne
   // @JoinColumn(name = "domanda_id")
   // private Domanda domanda;

    @OneToOne
    @JoinColumn(name = "domanda_id")
    private Domanda domanda;

    /*
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

*/


    public Risposta(String testoRisposta){
        this.testoRisposta = testoRisposta;
    }
    public Risposta(Long id, String testoRisposta, Integer punteggio) {
        this.id = id;
        this.testoRisposta = testoRisposta;
        this.punteggio = punteggio;
    }
    public Risposta(String testoRisposta, Integer punteggio,String testoValutazioneGpt) {
        this.testoRisposta = testoRisposta;
        this.punteggio = punteggio;
        this.testoValutazioneGpt = testoValutazioneGpt;
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


    //AGGIUTO ID UTENTE IN RISPOSTA

    /*
    public void setUtenteId(Utente u){
        this.utente.setId(u.getId());
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

     */
}
