package com.paw.controller;

import com.paw.model.Adopter;
import com.paw.model.Dog;
import com.paw.model.Fostering;
import com.paw.service.AdopterService;
import com.paw.service.DogService;
import com.paw.service.FosteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class FosteringController {

    private final FosteringService fosteringService;
    private final AdopterService adopterService;
    private final DogService dogService;

    @Autowired
    public FosteringController(FosteringService fosteringService, AdopterService adopterService, DogService dogService) {
        this.fosteringService = fosteringService;
        this.adopterService = adopterService;
        this.dogService = dogService;
    }

    @GetMapping("/foster")
    public ResponseEntity<List<Fostering>> getAllFosterings() {
        List<Fostering> fosterings = fosteringService.findAll();
        return ResponseEntity.ok(fosterings);
    }

    @PostMapping("/foster_add/{adopterId}/{dogId}")
    public ResponseEntity<?> addFostering(
            @PathVariable Long adopterId,
            @PathVariable Long dogId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // Fetch the adopter based on the adopterId
        Adopter adopter = adopterService.findById(adopterId);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter not found with id: " + adopterId);
        }

        // Fetch the dog based on the dogId
        Optional<Dog> optionalDog = dogService.getDogById(dogId);
        if (optionalDog.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dog not found with id: " + dogId);
        }

        Dog dog = optionalDog.get();

        Fostering fostering = new Fostering();
        fostering.setStartDate(LocalDate.now()); // Set start date as current date
        fostering.setEndDate(endDate); // Set provided end date
        fostering.setAdopter(adopter);
        fostering.setDog(dog);
        fostering.setFostererName(adopter.getName()); // Set fosterer name based on adopter's name

        Fostering addedFostering = fosteringService.add(fostering);
        return new ResponseEntity<>(addedFostering, HttpStatus.CREATED);
    }
    //here endDate data is passed through param in postman as endDate is defined as a parameter


    @DeleteMapping("/foster_delete/{id}")
    public ResponseEntity<Void> deleteFostering(@PathVariable Long id) {
        Optional<Adopter> optionalFostering = fosteringService.findById(id);
        if (optionalFostering.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        fosteringService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/foster_search")
    public ResponseEntity<List<Fostering>> searchFosteringsByAdopterName(@RequestParam String name) {
        List<Fostering> fosterings = fosteringService.findByAdopterNameContainsIgnoreCase(name);
        return ResponseEntity.ok(fosterings);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An error occurred");
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
