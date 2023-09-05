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
import java.util.List;
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

        Domanda primaDomanda = gptService.generaDomanda(nuovoColloquio);

        // Restituisci la prima domanda come risposta al client
        return ResponseEntity.ok("Colloquio ID: " +nuovoColloquio.getId() + "\n" + primaDomanda.getTestoDomanda());
    }

    //////////////////TEST:
    @GetMapping(value = "/listaDiDomande/{id}")
    public ResponseEntity<?> ottieniListaUtenti(@PathVariable Long id){
        Colloquio colloquio = colloquioRepository.findById(id).orElse(null);
        Domanda domanda2 = colloquioRepository.findById(id).get().getDomandaList().get(0);
        assert colloquio != null;
        List<Domanda> listaDiDomande = colloquio.getDomandaList();
        Domanda domanda = listaDiDomande.get(0);
        if (domanda == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("domande esistono");
    }
    /////////////////////////

    @PostMapping("/risposta/{colloquioId}")
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
                return ResponseEntity.ok("Punteggio ottenuto = " + rispostaUtente.getPunteggio() + "\nValutazione alla risposta :" + rispostaTesto + "\nCommento: " + commentoAllaRisposta
                        + "\nQuesta è la prossima domanda: \n" + prossimaDomanda.getTestoDomanda());
            } else {
                // Gestisci il caso in cui la lista delle domande è vuota
                return ResponseEntity.badRequest().body("La lista delle domande è vuota.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }




}
