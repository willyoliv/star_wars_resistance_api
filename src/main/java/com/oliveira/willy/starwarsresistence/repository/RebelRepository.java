package com.oliveira.willy.starwarsresistence.repository;

import com.oliveira.willy.starwarsresistence.model.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
}
