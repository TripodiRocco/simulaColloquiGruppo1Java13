package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.model.Risposta;
import com.example.chatbotColloquio.repository.ColloquioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService implements GptService{
    @Autowired
    private RestTemplate restTemplate;
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

    private Domanda chiamataApiGenerazioneGpt2(Colloquio colloquio) {
        // Effettua una chiamata all'API di ChatGPT per generare una domanda
        // Restituisci la domanda generata
        return null; // domandaGenerata;




    }

    private Domanda chiamataApiGenerazioneGpt(Colloquio colloquio) {
        // Configura l'URL dell'API di generazione GPT
        String apiUrl = "URL_API_GPT";

        // Costruisci il corpo della richiesta (può variare in base alle API)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("colloquioId", colloquio.getId()); // Esempio di dato da inviare

        // Configura l'header della richiesta (può variare in base alle API)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer TOKEN_GPT"); // Esempio di token di autenticazione

        // Costruisci l'oggetto della richiesta
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Effettua la chiamata all'API
        ResponseEntity<Domanda> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Domanda.class);

        // Restituisci la domanda generata dalla risposta dell'API
        return responseEntity.getBody();
    }

    private int chiamataApiValutazioneGpt(String rispostaUtente) {
        // Effettua una chiamata all'API di ChatGPT per ottenere il punteggio di valutazione
        // Restituisci il punteggio ottenuto
        return 0; //punteggio;
    }
}
