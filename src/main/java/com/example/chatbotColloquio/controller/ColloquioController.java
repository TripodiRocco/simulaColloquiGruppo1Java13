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



    //////////////////TEST:
    @GetMapping(value = "/listaDiDomande/{id}")
    public ResponseEntity<?> ottieniListaUtenti(@PathVariable Long id){
        Colloquio colloquio = colloquioRepository.findById(id).orElse(null);
        Domanda domanda2 = colloquioRepository.findById(id).get().getDomandaList().get(0);
        assert colloquio != null;
        List<Domanda> listaDiDomande = colloquio.getDomandaList();
        Domanda domanda = listaDiDomande.get(0);
        if (domanda == null){                           // COME SOSTITUIRE QUI
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("domande esistono");
    }
    /////////////////////////

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

    @GetMapping(value = "/domandeProva2")
    public ResponseEntity<?> listaProva() {
        try {
            // Creazione di un esempio di colloquio
            Utente utenteP = new Utente("Vin", "Mer", "VINME", "CIAO");
            utenteRepository.save(utenteP);

            Colloquio colloquio = new Colloquio();
            colloquio.setArgomentoColloquio("Java");
            colloquio.setDifficolta(5);
            colloquio.setUtente(utenteP);
            LocalDateTime nuovoOrario = LocalDateTime.of(2023, 8, 30, 10, 0);
            colloquio.setOrario(nuovoOrario);

            // Creazione di domande e risposte associate al colloquio
            Domanda domanda1 = new Domanda("Domanda1");
            Risposta risposta1 = new Risposta("Risposta Utente 1");
            risposta1.setTestoValutazioneGpt("Commento GPT 1");
            risposta1.setPunteggio(10);

            domanda1.setRisposta(risposta1);
            domanda1.setColloquio(colloquio);

            // Aggiunta delle domande al colloquio
            colloquio.getDomandaList().add(domanda1);

            // Salvare il colloquio dopo aver aggiunto le domande
            colloquioRepository.save(colloquio);

            // Salvare le entità collegate
            domandaRepository.save(domanda1);
            rispostaRepository.save(risposta1);

            // Restituzione del colloquio completo con domande e risposte
            return ResponseEntity.ok(colloquio);
        } catch (Exception e) {
            // Gestione dell'eccezione e invio di una risposta HTTP adeguata
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'elaborazione della richiesta.");
        }
    }


    @GetMapping("/{colloquioId}/domande-risposte")
    public ResponseEntity<?> getDomandeRisposteByColloquio(@PathVariable Long colloquioId) {
        Optional<List<Domanda>> domandeOptional = colloquioService.getDomandeRisposteByColloquio(colloquioId);

        if (domandeOptional.isPresent()) {
            List<Domanda> domande = domandeOptional.get();
            return ResponseEntity.ok(domande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
