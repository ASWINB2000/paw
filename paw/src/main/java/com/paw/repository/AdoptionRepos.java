package com.paw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paw.model.Adoption;
@Repository
public interface AdoptionRepos extends JpaRepository<Adoption, Long>{
	List<Adoption> findByAdopter_NameContainsIgnoreCase(String name);

}
