package com.example.interviewMe.service;

import com.example.interviewMe.model.Colloquio;
import com.example.interviewMe.model.Domanda;
import com.example.interviewMe.model.Risposta;

public interface GptService {

    Domanda generaDomanda(Colloquio colloquio);
    String valutaERispondi(Colloquio colloquio, Domanda domanda, Risposta risposta);
}
