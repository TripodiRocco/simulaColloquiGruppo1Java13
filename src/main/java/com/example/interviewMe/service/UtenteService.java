package com.example.interviewMe.service;

import com.example.interviewMe.model.Utente;
import com.example.interviewMe.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;
    public Optional<Utente> getUtente(Long id){
        Optional<Utente> utenteOptional = utenteRepository.findById(id);
        return utenteOptional;
    }
    public List<Utente> getAllUtenti(){
        List<Utente> listaDiUtenti = utenteRepository.findAll();
        return listaDiUtenti;
    }
    public Utente insertUtente(Utente utente){
        Utente nuovoUtente = utenteRepository.save(utente);
        return nuovoUtente;
    }


    public Optional<Utente> updateUtente(Long id, Utente utente){
        Optional<Utente> user = utenteRepository.findById(id);

        if(user.isPresent()) {
                user.get().setNome(utente.getNome());
                user.get().setCognome(utente.getCognome());
                user.get().setPassword(utente.getPassword());
                user.get().setUsername(utente.getUsername());

             utenteRepository.save(user.get());
             return user;
        }else
            return Optional.empty();

    }

    public Utente deleteUtente(Long id){
        Utente user = utenteRepository.findById(id).orElse(null);
        assert user != null;    // DA VEDERE ANCHE QUI SE SOSTITUIRE O MENO
        utenteRepository.delete(user);
        return  user;
    }


}
