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

import com.paw.model.Adopter;
import com.paw.service.AdopterService;

import jakarta.validation.Valid;

@RestController
public class AdopterController {

    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }
    

    @GetMapping("/adopters")
    public ResponseEntity<List<Adopter>> getAllAdopters() {
        List<Adopter> adopters = adopterService.findAll();
        return ResponseEntity.ok(adopters);
    }

    @GetMapping("/adopters/{id}")
    public ResponseEntity<Adopter> getAdopterById(@PathVariable Long id) {
        return adopterService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/adoptersAdd")
    public ResponseEntity<Adopter> addAdopter(@RequestBody @Valid Adopter adopter) {
    	Long userId = adopter.getId();
    	 if (userId == null) {
    	        // If userId is not provided in the adopter object, assign a default value
    	        userId = 1L; // Replace DEFAULT_USER_ID with your desired default value
    	    }
    	System.out.println(userId);
        Adopter savedAdopter = adopterService.save(userId,adopter);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdopter);
    }

    @PutMapping("/adoptersUpdate/{id}")
    public ResponseEntity<Adopter> updateAdopter(@PathVariable Long id, @RequestBody @Valid Adopter adopter) {
        return adopterService.findById(id)
                .map(existingAdopter -> {
                    existingAdopter.setId(id); // Ensure the ID matches the path variable
                    // Assuming user ID is retrievable from the adopter object or from the authentication context
                    Long userId = existingAdopter.getId(); // Adjust this according to your actual implementation
                    Adopter updatedAdopter = adopterService.save(userId, existingAdopter);
                    return ResponseEntity.ok(updatedAdopter);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/adoptersDelete/{id}")
    public ResponseEntity<Void> deleteAdopter(@PathVariable Long id) {
        if (!adopterService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adopterService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors());
    }
}
