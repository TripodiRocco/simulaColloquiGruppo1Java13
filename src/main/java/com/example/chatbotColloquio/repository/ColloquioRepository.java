package com.example.chatbotColloquio.repository;

import com.example.chatbotColloquio.model.Colloquio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColloquioRepository extends JpaRepository<Colloquio, Long> {

    /*

    Decidere cosa farne di questo metodo

    @Override
    Optional<Colloquio> findById(Long aLong);

     */

}
