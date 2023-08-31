package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.model.Risposta;
import com.example.chatbotColloquio.repository.ColloquioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatGptService implements GptService{
    @Autowired
    private ColloquioRepository colloquioRepository;


    @Override
    public Domanda generaDomanda(Colloquio colloquio) {
        Domanda domandaGenerata = chiamataApiGenerazioneGpt(colloquio);

        // Aggiungi la domanda generata alla lista di domande del colloquio
        colloquio.getDomandaList().add(domandaGenerata);

        // Salva il colloquio nel database
        colloquioRepository.save(colloquio);

        return domandaGenerata;
    }



    @Override
    public void valutaERispondi(Colloquio colloquio, Domanda domanda, Risposta rispostaUtente) {
        int punteggioDaGpt = chiamataApiValutazioneGpt(rispostaUtente.getTestoRisposta());

        // Aggiorna la risposta utente con il punteggio ottenuto
        rispostaUtente.setPunteggio(punteggioDaGpt);

        // Collega la risposta utente alla domanda
        domanda.setRisposta(rispostaUtente);

        // Salva il colloquio nel repository
        colloquioRepository.save(colloquio);
    }

    private Domanda chiamataApiGenerazioneGpt(Colloquio colloquio) {
        // Effettua una chiamata all'API di ChatGPT per generare una domanda
        // Restituisci la domanda generata
        return null; // domandaGenerata;
    }

    private int chiamataApiValutazioneGpt(String rispostaUtente) {
        // Effettua una chiamata all'API di ChatGPT per ottenere il punteggio di valutazione
        // Restituisci il punteggio ottenuto
        return 0; //punteggio;
    }
}
