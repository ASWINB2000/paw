package com.paw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paw.model.Adopter;
@Repository
public interface AdopterRepos extends JpaRepository<Adopter, Long> {
	List<Adopter> findByNameContainsIgnoreCase(String name);
}
