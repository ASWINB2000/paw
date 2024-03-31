package com.paw.repository;

import com.paw.model.Fostering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FosteringRepos extends JpaRepository<Fostering, Long> {
    List<Fostering> findByAdopter_NameContainsIgnoreCase(String name);
}
