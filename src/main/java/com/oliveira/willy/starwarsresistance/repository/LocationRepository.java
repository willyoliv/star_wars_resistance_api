package com.oliveira.willy.starwarsresistance.repository;

import com.oliveira.willy.starwarsresistance.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
