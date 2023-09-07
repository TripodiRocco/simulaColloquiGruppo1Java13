package com.example.chatbotColloquio.controller;

import com.example.chatbotColloquio.model.Utente;
import com.example.chatbotColloquio.repository.UtenteRepository;
import com.example.chatbotColloquio.service.UtenteService;
import io.swagger.v3.oas.annotations.Operation;
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
    UtenteRepository utenteRepository;

    @Autowired
    UtenteService utenteService;

    //READ USER BY ID

    @GetMapping(value = "/findById/{id}")
    @Operation(summary = "Ricerca un utente specifico inserendo il suo identificativo")
    public ResponseEntity<Optional<Utente>> ricercaUtente(@PathVariable Long id){
        Optional<Utente> utenteOptional = utenteRepository.findById(id);
        // Utente utente = utenteRepository.findById(id).orElse(null);              // SOSTITUZIONE CON OPTIONAL
        if(!utenteOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utenteOptional);
    }

    //READ ALL
    @GetMapping(value = "/listaDiUtenti")
    @Operation(summary = "Ritorna indietro un elenco degli utenti memorizzato nel database")
    public ResponseEntity<?> ottieniListaUtenti(){
        List<Utente> listaDiUtenti = utenteRepository.findAll();

        if (listaDiUtenti.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaDiUtenti);
    }


    //CREATE
    @PostMapping(value = "/creaUtente")
    @Operation(summary = "Crea un utente inserendo i parametri richiesti, seguendo l'esempio")
    public ResponseEntity<Utente> creaUtente(@RequestBody Utente utente){
        Utente nuovoUtente = utenteRepository.save(utente);
        return ResponseEntity.ok(nuovoUtente);
    }


    // DA ELIMINARE IN SEGUITO, SOLO PER PROVA

    @GetMapping(value = "/aggiungiUtenti")
    @Operation(summary = "Carica un elenco di utenti pronto per essere memorizzato nel database")
    public ResponseEntity<String> aggiungiUtente(){
        Utente utente1 = new Utente("rocchino", "ciao", "Rocco", "Tripodi");
        Utente utente2 = new Utente("giuseppino", "ciao2", "Giuseppe", "Bronzellino");
        Utente utente3 = new Utente("vincenzino", "ciao3", "Vincenzo", "Merola");
        Utente utente4 = new Utente("pietrino", "ciao4", "Pietro", "Benedicenti");

        utenteRepository.save(utente1);
        utenteRepository.save(utente2);
        utenteRepository.save(utente3);
        utenteRepository.save(utente4);

        return ResponseEntity.ok("lista utenti creata");
    }

    //UPDATE
    @PutMapping(value = "/modificaUtente/{id}")
    @Operation(summary = "Modifica identificativo e altri parametri di un utente")
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
