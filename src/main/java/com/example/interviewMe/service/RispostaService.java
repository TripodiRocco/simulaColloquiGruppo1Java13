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
        Optional<Risposta> utenteOptional = rispostaRepository.findById(id);
        return utenteOptional;
    }
    public List<Risposta> getAllRisposte(){
        List<Risposta> listaDiRisposte = rispostaRepository.findAll();
        return listaDiRisposte;
    }
    public Risposta insertRisposte(Risposta risposta){
        Risposta r = rispostaRepository.save(risposta);
        return r;
    }


    public Optional<Risposta> updateRisposte(Long id, Risposta risposta){
        Optional<Risposta> r = rispostaRepository.findById(id);

        if(r.isPresent()) {
            r.get().setTestoRisposta(risposta.getTestoRisposta());
            r.get().setPunteggio(risposta.getPunteggio());
            r.get().setTestoValutazioneGpt(risposta.getTestoValutazioneGpt());

            rispostaRepository.save(r.get());
            return r;
        }else
            return Optional.empty();

    }

    public Risposta deleteRisposte(Long id){
        Risposta r = rispostaRepository.findById(id).orElse(null);
        assert r != null;    // DA VEDERE ANCHE QUI SE SOSTITUIRE O MENO
        rispostaRepository.delete(r);
        return  r;
    }
}
