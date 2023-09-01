package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Utente;
import com.example.chatbotColloquio.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;


    public Optional<Utente> updateUtente(Long id, Utente utente){
        Optional<Utente> user = utenteRepository.findById(id);

        if(user.isPresent()) {
            //user.setId(utente.getId());
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
        assert user != null;
        utenteRepository.delete(user);
        return  user;
    }


}
