package com.oliveira.willy.starwarsresistence.repository;

import com.oliveira.willy.starwarsresistence.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
