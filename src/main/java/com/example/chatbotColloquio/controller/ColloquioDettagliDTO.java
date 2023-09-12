package com.example.chatbotColloquio.controller;

import java.util.List;

public class ColloquioDettagliDTO {
    private Long colloquioId;
    private List<DomandaRispostaDTO> domandeERisposte;

    public ColloquioDettagliDTO(Long colloquioId, List<DomandaRispostaDTO> domandeERisposte) {
        this.colloquioId = colloquioId;
        this.domandeERisposte = domandeERisposte;
    }

    public Long getColloquioId() {
        return colloquioId;
    }

    public void setColloquioId(Long colloquioId) {
        this.colloquioId = colloquioId;
    }

    public List<DomandaRispostaDTO> getDomandeERisposte() {
        return domandeERisposte;
    }

    public void setDomandeERisposte(List<DomandaRispostaDTO> domandeERisposte) {
        this.domandeERisposte = domandeERisposte;
    }
}