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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
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
    public ResponseEntity<String> iniziaColloquio(@PathVariable Long utenteId, @RequestParam String argomentoColloquio, @RequestParam int difficolta) {

       Optional<Utente> utente = utenteRepository.findById(utenteId);



       if(!utente.isPresent()){
           return ResponseEntity.notFound().build();
        }


        Colloquio nuovoColloquio = new Colloquio();

        nuovoColloquio.setUtente(utente.get());

        nuovoColloquio.setArgomentoColloquio(argomentoColloquio);
        nuovoColloquio.setDifficolta(difficolta);
        LocalDateTime nuovoOrario = LocalDateTime.of(2023, 8, 30, 10, 0);
        nuovoColloquio.setOrario(nuovoOrario);



        /*
        //bisogna salvare il colloquio nella lista di colloqui presente
          nella entity colloquio e forse non il contrario come fatto su

          adesso si parte da colloquio e si salva l'utente in colloquio
          ma bisogna partire da utente e salvare i colloqui nella lista

          STRUTTURA:
          ---------------------------------
          utente_id:
          ---------------------------------
                colloquio1:
                    Domanda1:
                        RispostaUtente
                        ValutazioneGPT
                    Domanda2:
                        RispostaUtente
                        ValutazioneGPT
          ---------------------------------
                colloquio2:
                    Domanda1:
                        RispostaUtente
                        ValutazioneGPT
          ---------------------------------

        utente.setColloquio(colloquio);
        utenteRepository.save(utente);
        */
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
            Utente utente = colloquio.getUtente(); // Assicurati che l'utente sia correttamente inizializzato

            // Verifica se la lista delle domande è vuota
            if (!colloquio.getDomandaList().isEmpty()) {
                Domanda ultimaDomanda = colloquio.getDomandaList().get(colloquio.getDomandaList().size() - 1);
                Risposta rispostaUtente = new Risposta();
                rispostaUtente.setTestoRisposta(rispostaTesto);
                String commentoAllaRisposta = gptService.valutaERispondi(colloquio, ultimaDomanda, rispostaUtente);

                Domanda prossimaDomanda = gptService.generaDomanda(colloquio);
                // Restituisci la prossima domanda come risposta al client
                return ResponseEntity.ok("Valutazione alla risposta utente:" + rispostaTesto + "\nCommento: " + commentoAllaRisposta
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

    //NON FUNZIONA CORRETTAMENTE
    /*
    @GetMapping("/utente/{utenteId}/dettagli")
    public ResponseEntity<UtenteDettagliDTO> getUtenteDettagli(@PathVariable Long utenteId) {
        Optional<Utente> utenteOptional = utenteRepository.findById(utenteId);

        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            Optional<Colloquio> colloqui = colloquioRepository.findById(utente.getId());
            List<ColloquioDettagliDTO> colloquiDettagli = colloqui.stream()
                    .map(colloquio -> {
                        List<Domanda> domande = colloquio.getDomandaList();
                        List<DomandaRispostaDTO> domandeERisposte = domande.stream()
                                .map(domanda -> {
                                    Risposta risposta = domanda.getRisposta();
                                    String rispostaText = (risposta != null) ? risposta.getTestoRisposta() : "";
                                    String valutazioneGpt = (risposta != null) ? risposta.getTestoValutazioneGpt() : "";
                                    return new DomandaRispostaDTO(
                                            domanda.getTestoDomanda(),
                                            rispostaText,
                                            valutazioneGpt
                                    );
                                })
                                .collect(Collectors.toList());

                        return new ColloquioDettagliDTO(
                                colloquio.getId(),
                                domandeERisposte
                        );
                    })
                    .collect(Collectors.toList());

            UtenteDettagliDTO utenteDettagli = new UtenteDettagliDTO(
                    utente.getId(),
                    utente.getNome(),
                    colloquiDettagli
            );

            return ResponseEntity.ok(utenteDettagli);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     */

    //FUNZIONANTE
    @GetMapping("/{userId}/dettagli")
    public ResponseEntity<UtenteDettagliDTO> getUtentiDettagli(@PathVariable Long userId) {
      //  Utente utente = utenteRepository.findById(userId).orElse(null);
        Optional<Utente> utenteOptional = utenteRepository.findById(userId);

      //  if (utente != null) {
        if(utenteOptional.isPresent()){
            Utente utente = utenteOptional.get();
            List<ColloquioDettagliDTO> colloquiDettagli = new ArrayList<>();

          for (Colloquio colloquio : utente.getColloqui()) {
             List<DomandaRispostaDTO> domandeERisposte = new ArrayList<>();

                for (Domanda domanda : colloquio.getDomandaList()) {
                    Risposta risposta = domanda.getRisposta();
                    DomandaRispostaDTO domandaRispostaDTO = new DomandaRispostaDTO(
                            domanda.getTestoDomanda(),
                            (risposta != null) ? risposta.getTestoRisposta() : "TESTO RISPOSTA UTENTE NON TROVATo",
                            (risposta != null) ? risposta.getTestoValutazioneGpt() : "TESTO RISPOSTA GPT NON TROVATO"
                    );

                    domandeERisposte.add(domandaRispostaDTO);
                }

                ColloquioDettagliDTO colloquioDettagliDTO = new ColloquioDettagliDTO(
                        colloquio.getId(),
                        domandeERisposte
                );

                colloquiDettagli.add(colloquioDettagliDTO);
            }

            UtenteDettagliDTO utenteDettagliDTO = new UtenteDettagliDTO(
                    utente.getId(),
                    utente.getNome(),
                    colloquiDettagli
            );

            return ResponseEntity.ok(utenteDettagliDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //METODO DI PROVA CORRETTA SEQUENZA DI SALVATAGGIO

    /*
    @GetMapping("/{userId}/provaFunzionamento")
    public ResponseEntity<?> ProvaFunzionamento(@PathVariable Long userId) {

        Utente utente = utenteRepository.findById(userId).orElse(null);
        assert utente != null;
        utenteRepository.save(utente);

        Colloquio nuovoColloquio = new Colloquio();

        nuovoColloquio.setUtente(utente);

        nuovoColloquio.setArgomentoColloquio("Java");
        nuovoColloquio.setDifficolta(5);
        LocalDateTime nuovoOrario = LocalDateTime.of(2023, 8, 30, 10, 0);
        nuovoColloquio.setOrario(nuovoOrario);
        colloquioRepository.save(nuovoColloquio);

        Domanda domanda = new Domanda("Domanda 1");
        domanda.setColloquio(nuovoColloquio);

        domandaRepository.save(domanda);

        Risposta risposta = new Risposta();
        risposta.setTestoRisposta("Risposta 1 utente ");
        risposta.setTestoValutazioneGpt("Risposta 1 GPT");
        risposta.setPunteggio(8);

        risposta.setDomanda(domanda);
        rispostaRepository.save(risposta);

        domanda.setRisposta(risposta);
        domandaRepository.save(domanda);

        return ResponseEntity.ok("DATI INSERITI CORRETTAMENTE");
    }

     */
}
