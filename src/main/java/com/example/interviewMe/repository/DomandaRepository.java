package com.example.interviewMe.repository;

import com.example.interviewMe.model.Domanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomandaRepository extends JpaRepository<Domanda, Long> {

}
