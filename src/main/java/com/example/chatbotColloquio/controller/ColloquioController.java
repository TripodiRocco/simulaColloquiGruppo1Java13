package com.example.chatbotColloquio.controller;

import com.example.chatbotColloquio.model.Risposta;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Utente;
import com.example.chatbotColloquio.repository.ColloquioRepository;
import com.example.chatbotColloquio.repository.UtenteRepository;
import com.example.chatbotColloquio.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class ColloquioController {
    @Autowired
    private GptService gptService;

    @Autowired
    private ColloquioRepository colloquioRepository;    // cancellare

    @Autowired
    private UtenteRepository utenteRepository;  // cancellare
    @PostMapping("/inizia/{utenteId}")
    public ResponseEntity<String> iniziaColloquio(@PathVariable Long utenteId) {

        Optional<Utente> utente = utenteRepository.findById(utenteId);
        if(!utente.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Colloquio nuovoColloquio = new Colloquio();
        nuovoColloquio.setUtente(utente.get());

        nuovoColloquio.setArgomentoColloquio("Java");
        nuovoColloquio.setDifficolta(5);
        nuovoColloquio.setUtente(utente.get());
        LocalDateTime nuovoOrario = LocalDateTime.of(2023, 8, 30, 10, 0);
        nuovoColloquio.setOrario(nuovoOrario);


        colloquioRepository.save(nuovoColloquio);

        Domanda primaDomanda = gptService.generaDomanda(nuovoColloquio);

        // Restituisci la prima domanda come risposta al client
        return ResponseEntity.ok(primaDomanda.getTestoDomanda());
    }

    //TEST:
    @GetMapping("/prova")
    public ResponseEntity<String>  domanda(){

        return ResponseEntity.ok("Test ok");
    }
    @PostMapping("/risposta")
    public ResponseEntity<String> gestisciRisposta(@RequestParam Long colloquioId, @RequestBody String rispostaTesto) {
        Optional<Colloquio> colloquioOptional = colloquioRepository.findById(colloquioId);
        if (colloquioOptional.isPresent()) {
            Colloquio colloquio = colloquioOptional.get();
            Domanda ultimaDomanda = colloquio.getDomandaList().get(colloquio.getDomandaList().size() - 1);
            Risposta risposta = new Risposta(rispostaTesto);

            gptService.valutaERispondi(colloquio, ultimaDomanda, risposta);

            Domanda prossimaDomanda = gptService.generaDomanda(colloquio);

            // Restituisci la prossima domanda come risposta al client
            return ResponseEntity.ok(prossimaDomanda.getTestoDomanda());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
