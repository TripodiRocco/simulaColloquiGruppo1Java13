package com.example.interviewMe.service;

import com.example.interviewMe.model.Domanda;
import com.example.interviewMe.model.Risposta;
import com.example.interviewMe.repository.DomandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomandaService {
    @Autowired
    DomandaRepository domandaRepository;

    public Optional<Domanda> getDomanda(Long id){
        Optional<Domanda> domandaOptional = domandaRepository.findById(id);
        return domandaOptional;
    }
    public List<Domanda> getAllDomande(){
        List<Domanda> listaDiDomande = domandaRepository.findAll();
        return listaDiDomande;
    }
    public Domanda insertDomande(Domanda domanda){
        Domanda d = domandaRepository.save(domanda);
        return d;
    }

    public Domanda deleteDomanda(Long id){
        Domanda d = domandaRepository.findById(id).orElse(null);
        assert d != null;
        domandaRepository.delete(d);
        return  d;
    }
}
