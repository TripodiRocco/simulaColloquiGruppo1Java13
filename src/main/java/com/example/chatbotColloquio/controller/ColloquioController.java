package com.example.chatbotColloquio.controller;

import com.example.chatbotColloquio.model.Risposta;
import com.example.chatbotColloquio.model.Domanda;
import com.example.chatbotColloquio.model.Colloquio;
import com.example.chatbotColloquio.model.Utente;
import com.example.chatbotColloquio.repository.ColloquioRepository;
import com.example.chatbotColloquio.repository.DomandaRepository;
import com.example.chatbotColloquio.repository.RispostaRepository;
import com.example.chatbotColloquio.repository.UtenteRepository;
import com.example.chatbotColloquio.service.ColloquioService;
import com.example.chatbotColloquio.service.GptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chatbotColloquio.controller.DomandaRispostaDTO;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Gestione colloquio", description = "API per la gestione del colloquio")
public class ColloquioController {

    @Autowired
    private DomandaRepository domandaRepository;

    @Autowired
    private RispostaRepository rispostaRepository;
    @Autowired
    private ColloquioService colloquioService;
    @Autowired
    private GptService gptService;

    @Autowired
    private ColloquioRepository colloquioRepository;    // cancellare

    @Autowired
    private UtenteRepository utenteRepository;  // cancellare


    @PostMapping("/inizia/{utenteId}")
    @Operation(summary = "Inserito l'identificativo dell'utente da colloquiare, permette di iniziare il colloquio rivelando la domanda")
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

        Domanda primaDomanda = gptService.generaDomanda(nuovoColloquio);

        // Restituisci la prima domanda come risposta al client
        return ResponseEntity.ok("Colloquio ID: " +nuovoColloquio.getId() + "\n" + primaDomanda.getTestoDomanda());
    }



    @PostMapping("/risposta/{colloquioId}")
    @Operation(summary = "Inserito l'identificativo dell'utente, egli può rispondere alla domanda posta dal recruiter, che assegna un punteggio da 1 a 9 al candidato per il grado di conoscenza a cui segue un commento; e si prosegue con la prossima domanda")
    public ResponseEntity<String> gestisciRisposta(@PathVariable Long colloquioId ,@RequestBody String rispostaTesto) {
        Optional<Colloquio> colloquioOptional = colloquioRepository.findById(colloquioId);

        if (colloquioOptional.isPresent()) {
            Colloquio colloquio = colloquioOptional.get();
            // Verifica se la lista delle domande è vuota
            if (!colloquio.getDomandaList().isEmpty()) {
                Domanda ultimaDomanda = colloquio.getDomandaList().get(colloquio.getDomandaList().size() - 1);
                Risposta rispostaUtente = new Risposta();
                rispostaUtente.setTestoRisposta(rispostaTesto);
                String commentoAllaRisposta = gptService.valutaERispondi(colloquio, ultimaDomanda, rispostaUtente);

                Domanda prossimaDomanda = gptService.generaDomanda(colloquio);
                // Restituisci la prossima domanda come risposta al client
                return ResponseEntity.ok("Punteggio ottenuto = " + rispostaUtente.getPunteggio() + "\nValutazione alla risposta utente:" + rispostaTesto + "\nCommento: " + commentoAllaRisposta
                        + "\nQuesta è la prossima domanda: \n" + prossimaDomanda.getTestoDomanda());
            } else {
                // Gestisci il caso in cui la lista delle domande è vuota
                return ResponseEntity.badRequest().body("La lista delle domande è vuota.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }



    @GetMapping("/{colloquioId}/visualizzaDomande")
    public ResponseEntity<List<DomandaRispostaDTO>> getDomandeERisposteByColloquio(@PathVariable Long colloquioId) {
        Optional<Colloquio> colloquioOptional = colloquioRepository.findById(colloquioId);

        if (colloquioOptional.isPresent()) {
            Colloquio colloquio = colloquioOptional.get();
            List<Domanda> domande = colloquio.getDomandaList();
            List<DomandaRispostaDTO> domandeERisposte = domande.stream()
                    .map(domanda -> {
                        Risposta risposta = domanda.getRisposta();
                        String rispostaText = (risposta != null) ? risposta.getTestoRisposta() : "";
                        String valutazioneGpt = (risposta != null) ? risposta.getTestoValutazioneGpt() : "";
                        return new DomandaRispostaDTO(
                                colloquio.getId(),
                                domanda.getTestoDomanda(),
                                rispostaText,
                                valutazioneGpt
                        );
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(domandeERisposte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
