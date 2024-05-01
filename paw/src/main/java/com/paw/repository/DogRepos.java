package com.paw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paw.model.Dog;

public interface DogRepos extends JpaRepository<Dog, Long> {
    List<Dog> findByNameContainsIgnoreCase(String name);

	Optional<Dog> findByName(String fileName);

}
