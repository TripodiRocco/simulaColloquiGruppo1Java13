package com.example.interviewMe.controller;

import com.example.interviewMe.model.Utente;
import com.example.interviewMe.repository.UtenteRepository;
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
@Tag(name = "Gestione utenti", description = "API per la gestione degli utenti")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    //READ USER BY ID

    @GetMapping(value = "/findById/{id}")
    @Operation(summary = "Ricerca un utente specifico inserendo il suo identificativo")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Ricerca effettuata con successo")
    public ResponseEntity<Optional<Utente>> ricercaUtente(@PathVariable Long id){
        Optional<Utente> utenteOptional = utenteService.getUtente(id);
        if(!utenteOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utenteOptional);
    }

    //READ ALL
    @GetMapping(value = "/listaDiUtenti")
    @Operation(summary = "Ritorna indietro un elenco degli utenti memorizzato nel database")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Elenco utenti restituito con successo")
    public ResponseEntity<?> ottieniListaUtenti(){
        List<Utente> listaDiUtenti = utenteService.getAllUtenti();

        if (listaDiUtenti.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaDiUtenti);
    }


    //CREATE
    @PostMapping(value = "/creaUtente")
    @Operation(summary = "Crea un utente inserendo i parametri richiesti, seguendo l'esempio")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Utente creato con successo")
    public ResponseEntity<Utente> creaUtente(@RequestBody Utente utente){
        Utente nuovoUtente = utenteService.insertUtente(utente);
        return ResponseEntity.ok(nuovoUtente);
    }


    // DA ELIMINARE IN SEGUITO, SOLO PER PROVA

    @PostMapping(value = "/aggiungiUtenti")
    @Operation(summary = "Carica un elenco di utenti pronto per essere memorizzato nel database")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Utenti memorizzati nel database con successo")
    public ResponseEntity<String> aggiungiUtente(){
        Utente utente1 = new Utente("rocchino", "ciao", "Rocco", "Tripodi");
        Utente utente2 = new Utente("giuseppino", "ciao2", "Giuseppe", "Bronzellino");
        Utente utente3 = new Utente("vincenzino", "ciao3", "Vincenzo", "Merola");
        Utente utente4 = new Utente("pietrino", "ciao4", "Pietro", "Benedicenti");

        utenteService.insertUtente(utente1);
        utenteService.insertUtente(utente2);
        utenteService.insertUtente(utente3);
        utenteService.insertUtente(utente4);

        return ResponseEntity.ok("lista utenti creata");
    }

    //UPDATE
    @PutMapping(value = "/modificaUtente/{id}")
    @Operation(summary = "Modifica identificativo e altri parametri di un utente")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Modifica dell'utente avvenuta con successo")
    public ResponseEntity<Utente> modificaUtente(@PathVariable Long id, @RequestBody Utente utente){

        Optional<Utente> aggiornaUtente = utenteService.updateUtente(id, utente);

        if (aggiornaUtente.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aggiornaUtente.get());
    }

    //DELETE

    @DeleteMapping(value = "/eliminaUtente/{id}")
    @Operation(summary = "Elimina l'intero utente")
    @ApiResponse(responseCode = "200", description = "Con codice 200 OK: Eliminazione dell'utente avvenuta con successo")
    public ResponseEntity<String> eliminaUtente(@PathVariable Long id) {

        try {
            Utente user =  utenteService.deleteUtente(id);
            return ResponseEntity.ok("Utente "+ user.getNome() + " eliminato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore" +
                    " durante l'eliminazione dell'utente:\nutente non trovato");
        }
    }



}
