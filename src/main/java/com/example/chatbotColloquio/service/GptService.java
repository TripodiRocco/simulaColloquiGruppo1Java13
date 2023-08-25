package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.model.Risposta;

public interface GptService {

    Domanda generaDomanda(Colloquio colloquio);
    void valutaERispondi(Colloquio colloquio, Domanda domanda, Risposta risposta);
}
