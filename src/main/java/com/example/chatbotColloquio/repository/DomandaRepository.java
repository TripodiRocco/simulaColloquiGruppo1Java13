package com.example.chatbotColloquio.repository;

import com.example.chatbotColloquio.model.Domanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomandaRepository extends JpaRepository<Domanda, Long> {

}
