package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.repository.ColloquioRepository;
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
