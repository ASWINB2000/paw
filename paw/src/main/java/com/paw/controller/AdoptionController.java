package com.paw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.paw.model.Adopter;
import com.paw.model.Adoption;
import com.paw.model.Dog;
import com.paw.service.AdopterService;
import com.paw.service.AdoptionService;
import com.paw.service.DogService;

@RestController
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final AdopterService adopterService;
    private final DogService dogService;

    @Autowired
    public AdoptionController(AdoptionService adoptionService, AdopterService adopterService, DogService dogService) {
        this.adoptionService = adoptionService;
        this.adopterService = adopterService;
        this.dogService = dogService;
    }

    @GetMapping("/adoptions")
    public ResponseEntity<List<Adoption>> getAllAdoptions() {
        List<Adoption> adoptions = adoptionService.findAll();
        return ResponseEntity.ok(adoptions);
    }

    @PostMapping("/adoptions_add/{adopterId}/{dogId}")
    public ResponseEntity<?> addAdoption(
            @PathVariable Long adopterId,
            @PathVariable Long dogId) {

        Adopter adopter = adopterService.findById(adopterId);
        Optional<Dog> optionalDog = dogService.getDogById(dogId);

        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter not found with id: " + adopterId);
        }

        if (optionalDog.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dog not found with id: " + dogId);
        }

        Dog dog = optionalDog.get();

        // Create Adoption object with Hibernate automatically setting adoptionDate
        Adoption adoption = new Adoption();
        adoption.setAdopter(adopter);
        adoption.setDog(dog);

        Adoption addedAdoption = adoptionService.add(adoption);
        return new ResponseEntity<>(addedAdoption, HttpStatus.CREATED);
    }


//    @PutMapping("/adoptions_update/{id}")
//    public ResponseEntity<?> updateAdoption(@PathVariable Long id, @RequestBody Adoption adoption) {
//        Optional<Adoption> optionalAdoption = adoptionService.findById(id);
//        if (optionalAdoption.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adoption not found with id: " + id);
//        }
//
//        adoption.setId(id);
//        Adoption updatedAdoption = adoptionService.update(adoption);
//        return ResponseEntity.ok(updatedAdoption);
//    }
//adoption controller actually does not an update method as adoption is basically a log where you either make an adoption or you delete it.

    @DeleteMapping("/adoptions_delete/{id}")
    public ResponseEntity<Void> deleteAdoption(@PathVariable Long id) {
        Optional<Adoption> optionalAdoption = adoptionService.findById(id);
        if (optionalAdoption.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        adoptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/adoption_search")
    public ResponseEntity<List<Adoption>> searchAdoptionsByAdopterName(@RequestParam String name) {
        List<Adoption> adoptions = adoptionService.findByAdopterNameContainsIgnoreCase(name);
        return ResponseEntity.ok(adoptions);
    }

    // Exception handling for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}
