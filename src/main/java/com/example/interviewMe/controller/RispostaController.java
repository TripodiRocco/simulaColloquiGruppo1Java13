package com.example.interviewMe.controller;

import com.example.interviewMe.model.Risposta;
import com.example.interviewMe.model.Utente;
import com.example.interviewMe.service.RispostaService;
import com.example.interviewMe.service.UtenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@Tag(name = "Gestione risposte", description = "API per la gestione delle risposte")
public class RispostaController {
    @Autowired
    RispostaService rispostaService;

    //READ RISPOSTA BY ID

    @GetMapping(value = "/findRispostaById/{id}")
    @Operation(summary = "Ricerca una risposta specifica inserendo il suo identificativo")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Ricerca effettuata con successo")
    public ResponseEntity<Optional<Risposta>> ricercaRisposta(@PathVariable Long id){
        Optional<Risposta> r = rispostaService.getRisposta(id);
        if(!r.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    //READ ALL
    @GetMapping(value = "/listaDiRisposte")
    @Operation(summary = "Ritorna indietro un elenco delle risposte memorizzato nel database")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Elenco risposte restituito con successo")
    public ResponseEntity<?> ottieniListaRisposte(){
        List<Risposta> listaDirisposte = rispostaService.getAllRisposte();

        if (listaDirisposte.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaDirisposte);
    }


    // DA ELIMINARE IN SEGUITO, SOLO PER PROVA

    @PostMapping(value = "/aggiungiRisposte")
    @Operation(summary = "Carica un elenco di risposte pronto per essere memorizzato nel database")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Risposte memorizzate nel database con successo")
    public ResponseEntity<String> aggiungiRisposte(){
        Risposta r1 = new Risposta("questa e la prima risposta",70,"qui andra l valutazione di charGpt");
        Risposta r2 = new Risposta("questa e la seconda risposta",1,"qui andra l valutazione di charGpt");
        Risposta r3 = new Risposta("questa e la terza risposta",45,"qui andra l valutazione di charGpt");

        rispostaService.insertRisposte(r1);
        rispostaService.insertRisposte(r2);
        rispostaService.insertRisposte(r3);

        return ResponseEntity.ok("lista risposte create");
    }

    //DELETE

    @DeleteMapping(value = "/eliminaRisposta/{id}")
    @Operation(summary = "Elimina l'intera Risposta")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Eliminazione della Risposta avvenuta con successo")
    public ResponseEntity<String> eliminaRisposta(@PathVariable Long id) {

        try {
            Risposta r =  rispostaService.deleteRisposte(id);
            return ResponseEntity.ok("Risposta con id "+ r.getId() + " eliminata con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore" +
                    " durante l'eliminazione della Risposta:\nRisposta non trovato");
        }
    }
}
