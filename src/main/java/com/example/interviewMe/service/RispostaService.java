package com.example.interviewMe.service;

import com.example.interviewMe.model.Risposta;
import com.example.interviewMe.model.Utente;
import com.example.interviewMe.repository.RispostaRepository;
import com.example.interviewMe.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RispostaService {
    @Autowired
    RispostaRepository rispostaRepository;
    public Optional<Risposta> getRisposta(Long id){
        Optional<Risposta> rispostaOptional = rispostaRepository.findById(id);
        return rispostaOptional;
    }
    public List<Risposta> getAllRisposte(){
        List<Risposta> listaDiRisposte = rispostaRepository.findAll();
        return listaDiRisposte;
    }
    public Risposta insertRisposte(Risposta risposta){
        Risposta r = rispostaRepository.save(risposta);
        return r;
    }

    public Risposta deleteRisposte(Long id){
        Risposta r = rispostaRepository.findById(id).orElse(null);
        assert r != null;
        rispostaRepository.delete(r);
        return  r;
    }
}
