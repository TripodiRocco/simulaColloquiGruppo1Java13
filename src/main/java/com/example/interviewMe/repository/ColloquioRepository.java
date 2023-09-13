package com.example.interviewMe.repository;

import com.example.interviewMe.model.Colloquio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColloquioRepository extends JpaRepository<Colloquio, Long> {

    /*

    Decidere cosa farne di questo metodo

    @Override
    Optional<Colloquio> findById(Long aLong);

     */

}
