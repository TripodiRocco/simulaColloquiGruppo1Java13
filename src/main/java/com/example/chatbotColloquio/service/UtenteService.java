package com.example.chatbotColloquio.service;

import com.example.chatbotColloquio.model.Utente;
import com.example.chatbotColloquio.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;


    public Utente updateUtente(Long id, Utente utente){
        Utente user = utenteRepository.findById(id).orElse(null);

        if(user != null) {
            //user.setId(utente.getId());
            if (utente.getNome() != null)
                user.setNome(utente.getNome());
            if (utente.getCognome() != null)
                user.setCognome(utente.getCognome());
            if (utente.getPassword() != null)
                user.setPassword(utente.getPassword());
            if (utente.getUsername() != null)
                user.setUsername(utente.getUsername());

            return utenteRepository.save(user);
        }else
            return null;

    }

}
