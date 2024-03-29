package com.paw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paw.model.Breed;
import com.paw.repository.BreedRepos;


@Service
public class BreedService {
	private  BreedRepos breedRepository;

    @Autowired
    public BreedService(BreedRepos breedRepository) {
        this.breedRepository = breedRepository;
    }

    public List<Breed> getAllBreeds() {
        return breedRepository.findAll();
    }
    public Optional<Breed> getBreedbyId(Long id){
    	return breedRepository.findById(id);
    }
    public Breed addBreed(Breed breed) {
        return breedRepository.save(breed);
    }

    public Breed updateBreed(Long id, Breed breed) {
        breed.setId(id); // Ensure ID consistency
        return breedRepository.save(breed);
    }

    public void deleteBreed(Long id) {
        breedRepository.deleteById(id);
    }
 
}
