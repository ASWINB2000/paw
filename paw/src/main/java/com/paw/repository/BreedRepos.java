package com.paw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paw.model.Breed;
@Repository
public interface BreedRepos extends JpaRepository<Breed, Long>{
    List<Breed> findByNameContainsIgnoreCase(String name);

}
