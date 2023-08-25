package com.example.chatbotColloquio.repository;

import com.example.chatbotColloquio.model.Risposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RispostaRepository extends JpaRepository<Risposta, Long> {

}
