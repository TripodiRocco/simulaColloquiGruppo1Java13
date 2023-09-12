package com.example.chatbotColloquio.controller;

public class DomandaRispostaDTO {

  //  private Long idColloquio;
    private String domanda;
    private String testoRisposta;
    private String testoValutazioneGpt;

    public DomandaRispostaDTO(String testoDomanda, String rispostaText, String valutazioneGpt) {
       // this.idColloquio  = id;
        this.domanda=testoDomanda;
        this.testoRisposta=rispostaText;
        this.testoValutazioneGpt = valutazioneGpt;
    }
/*
    public Long getIdColloquio() {
        return idColloquio;
    }

    public void setIdColloquio(Long idColloquio) {
        this.idColloquio = idColloquio;
    }
*/
    public String getDomanda() {
        return domanda;
    }

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    public String getTestoRisposta() {
        return testoRisposta;
    }

    public void setTestoRisposta(String testoRisposta) {
        this.testoRisposta = testoRisposta;
    }

    public String getTestoValutazioneGpt() {
        return testoValutazioneGpt;
    }

    public void setTestoValutazioneGpt(String testoValutazioneGpt) {
        this.testoValutazioneGpt = testoValutazioneGpt;
    }
}
