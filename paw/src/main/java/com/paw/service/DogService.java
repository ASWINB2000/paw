package com.paw.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paw.model.Dog;
import com.paw.repository.DogRepos;
@Service
public class DogService {
	private  DogRepos dogRepository;

//    @Autowired
//    public DogService(DogRepos dogRepository) {
//        this.dogRepository = dogRepository;
//    }

    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public Dog addDog(Dog dog) {
        return dogRepository.save(dog);
    }

    public Dog updateDog(Long id, Dog dog) {
        dog.setId(id); // Ensure ID consistency
        return dogRepository.save(dog);
    }

    public void deleteDog(Long id) {
        dogRepository.deleteById(id);
    }

	public Optional<Dog> getDogById(Long id) {
		return dogRepository.findById(id);

	}

	
}
