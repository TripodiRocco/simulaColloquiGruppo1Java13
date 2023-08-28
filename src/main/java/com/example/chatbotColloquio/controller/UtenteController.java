package com.example.chatbotColloquio.controller;

import com.example.chatbotColloquio.model.Utente;
import com.example.chatbotColloquio.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class UtenteController {

    @Autowired
    UtenteRepository utenteRepository;

    @GetMapping(value = "/listaDiUtenti")
    public ResponseEntity<?> ottieniListaUtenti(){
        List<Utente> listaDiUtenti = utenteRepository.findAll();

        if (listaDiUtenti.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaDiUtenti);
    }

    @PostMapping(value = "/creaUtente")
    public ResponseEntity<Utente> creaUtente(@RequestBody Utente utente){
        Utente nuovoUtente = utenteRepository.save(utente);
        return ResponseEntity.ok(nuovoUtente);
    }


    // DA ELIMINARE IN SEGUITO, SOLO PER PROVA

    @GetMapping(value = "/aggiungiUtenti")
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

    @PutMapping(value = "/modificaUtente/{id}")
    public ResponseEntity<Utente> modificaUtente(@PathVariable Long id, @RequestParam Utente utente){
         Utente aggiornaUtente = utenteRepository.findById(id).orElse(null);
        if (aggiornaUtente == null){
            return ResponseEntity.notFound().build();
        } else {
            aggiornaUtente.setNome(utente.getNome());
            utenteRepository.save(aggiornaUtente);
        }

        return ResponseEntity.ok(aggiornaUtente);
    }


}
