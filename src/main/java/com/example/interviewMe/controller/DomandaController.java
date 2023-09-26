package com.example.interviewMe.controller;

import com.example.interviewMe.model.Domanda;
import com.example.interviewMe.model.Risposta;
import com.example.interviewMe.service.DomandaService;
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
@Tag(name = "Gestione domande", description = "API per la gestione delle domande")
public class DomandaController {
    @Autowired
    DomandaService domandaService;

    //READ DOMANDA BY ID
    @GetMapping(value = "/findDomandaById/{id}")
    @Operation(summary = "Ricerca una domanda specifica inserendo il suo identificativo")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Ricerca effettuata con successo")
    public ResponseEntity<Optional<Domanda>> ricercaDomande(@PathVariable Long id){
        Optional<Domanda> d = domandaService.getDomanda(id);
        if(!d.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d);
    }
    //READ ALL
    @GetMapping(value = "/listaDiDomande")
    @Operation(summary = "Ritorna indietro un elenco delle domande memorizzato nel database")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Elenco domande restituito con successo")
    public ResponseEntity<?> ottieniListaDomande(){
        List<Domanda> listaDiDomande = domandaService.getAllDomande();

        if (listaDiDomande.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaDiDomande);
    }
    //DELETE

    @DeleteMapping(value = "/eliminaDomanda/{id}")
    @Operation(summary = "Elimina l'intera Domanda")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Eliminazione della Domanda avvenuta con successo")
    public ResponseEntity<String> eliminaDomanda(@PathVariable Long id) {

        try {
            Domanda d =  domandaService.deleteDomanda(id);
            return ResponseEntity.ok("Domanda con id "+ d.getId() + " eliminata con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore" +
                    " durante l'eliminazione della Domanda:\nDomanda non trovato");
        }
    }
    // DA ELIMINARE IN SEGUITO, SOLO PER PROVA

    @PostMapping(value = "/aggiungiDomande")
    @Operation(summary = "Carica un elenco di domande pronto per essere memorizzato nel database")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Domande memorizzate nel database con successo")
    public ResponseEntity<String> aggiungiDomande(){
        Domanda d1 = new Domanda("qui andra il testo della domanda");
        Domanda d2 = new Domanda("qui andra il testo della domanda");
        Domanda d3 = new Domanda("qui andra il testo della domanda");


        domandaService.insertDomande(d1);
        domandaService.insertDomande(d2);
        domandaService.insertDomande(d3);

        return ResponseEntity.ok("lista domande create");
    }
}
