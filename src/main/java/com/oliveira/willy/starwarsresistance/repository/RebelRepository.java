package com.oliveira.willy.starwarsresistance.repository;

import com.oliveira.willy.starwarsresistance.model.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
}
