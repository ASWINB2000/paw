package com.paw.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.paw.model.Breed;
import com.paw.model.Dog;
import com.paw.model.Users;
import com.paw.service.BreedService;
import com.paw.service.DogService;
import com.paw.service.UsersService;
import jakarta.validation.Valid;

@RestController
public class DogController {

    private final DogService dogService;
    private final BreedService BreedService;
    private final UsersService UsersService;

    public DogController(DogService dogService, BreedService BreedService,UsersService UsersService) {
        this.dogService = dogService;
		this.BreedService = BreedService;
		this.UsersService=UsersService;
    }


    @GetMapping("/dogs")
    public ResponseEntity<List<Dog>> getAllDogs() {
        List<Dog> dogs = dogService.getAllDogs(); // Updated method call
        return ResponseEntity.ok(dogs);
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable Long id) {
        return dogService.getDogById(id) // Implement this method in DogService
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping("/dogs")
//    public ResponseEntity<Dog> addDog(@RequestBody @Valid Dog dog) {
//        Dog savedDog = dogService.addDog(dog); // Updated method call
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedDog);
//    }
    @PostMapping("/dogs/{breed_id}/{user_id}")
    public ResponseEntity<Dog> addDog(@RequestBody @Valid Dog dog,@PathVariable Long breed_id,@PathVariable Long user_id) {
    	 // Retrieve the Breed and User objects based on the provided IDs
    	Breed breed = BreedService.getBreedbyId(breed_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Breed not found with id: " + breed_id));
    	
    	Users user = UsersService.findByIdOrThrow(user_id);

        
        // Set the retrieved Breed and User objects to the Dog
        dog.setBreed(breed);
        dog.setUsers(user);
        Dog savedDog = dogService.addDog(dog); // Updated method call
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDog);
    }
    
    

    @PutMapping("/dogs/{id}")
    public ResponseEntity<Dog> updateDog(@PathVariable Long id, @RequestBody @Valid Dog updatedDog) {
        // Retrieve the existing dog from the database
        Dog existingDog = dogService.getDogById(id)
                                     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found with id: " + id));

        // Update the fields of the existing dog with the values from the updated dog
        existingDog.setName(updatedDog.getName());
        existingDog.setBirthDate(updatedDog.getBirthDate());
        existingDog.setGender(updatedDog.getGender());
        existingDog.setColour(updatedDog.getColour());
        existingDog.setDescription(updatedDog.getDescription());
        existingDog.setLocation(updatedDog.getLocation());

        // If the updated dog contains a breed, update it in the existing dog
        if (updatedDog.getBreed() != null) {
            Breed updatedBreed = updatedDog.getBreed();
            if (updatedBreed.getId() != null) {
                Breed breed = BreedService.getBreedbyId(updatedBreed.getId())
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Breed not found with id: " + updatedBreed.getId()));
                existingDog.setBreed(breed);
            } else {
                existingDog.setBreed(null); // Set breed to null if breed_id is not provided
            }
        }

        // If the updated dog contains a user, update it in the existing dog
        if (updatedDog.getUsers() != null) {
            Users updatedUser = updatedDog.getUsers();
            if (updatedUser.getId() != null) {
                Users user = UsersService.findByIdOrThrow(updatedUser.getId());
                existingDog.setUsers(user);
            } else {
                existingDog.setUsers(null); // Set user to null if user_id is not provided
            }
        }

        // Save the updated dog
        Dog savedDog = dogService.updateDog(id, existingDog);
        return ResponseEntity.ok(savedDog);
    }



    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id); // Updated method call
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors());
    }
}
