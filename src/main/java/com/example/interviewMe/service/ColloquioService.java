package com.example.interviewMe.service;

import com.example.interviewMe.model.Colloquio;
import com.example.interviewMe.model.Domanda;
import com.example.interviewMe.repository.ColloquioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ColloquioService {
    @Autowired
    private  ColloquioRepository colloquioRepository;


    public Optional<List<Domanda>> getDomandeRisposteByColloquio(Long colloquioId) {
        Optional<Colloquio> colloquioOptional = colloquioRepository.findById(colloquioId);

        if (colloquioOptional.isPresent()) {
            Colloquio colloquio = colloquioOptional.get();
            // Estrai la lista delle domande con relative risposte e valutazioni GPT
            List<Domanda> domande = colloquio.getDomandaList();
          //  return Optional.of(domande);
            return Optional.ofNullable(domande);
        } else {
            // Gestisce il caso in cui il colloquio non è stato trovato
            return Optional.empty();
        }
    }

}
