package com.example.interviewMe.repository;

import com.example.interviewMe.model.Risposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RispostaRepository extends JpaRepository<Risposta, Long> {

}
