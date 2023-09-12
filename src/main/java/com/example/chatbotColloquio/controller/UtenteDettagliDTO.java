package com.example.chatbotColloquio.controller;

import java.util.List;

public class UtenteDettagliDTO {
    private Long utenteId;
    private String nome;
    private List<ColloquioDettagliDTO> colloquiDettagli;

    public UtenteDettagliDTO(Long utenteId, String nome, List<ColloquioDettagliDTO> colloquiDettagli) {
        this.utenteId = utenteId;
        this.nome = nome;
        this.colloquiDettagli = colloquiDettagli;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ColloquioDettagliDTO> getColloquiDettagli() {
        return colloquiDettagli;
    }

    public void setColloquiDettagli(List<ColloquioDettagliDTO> colloquiDettagli) {
        this.colloquiDettagli = colloquiDettagli;
    }
}