package com.paw.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paw.model.Adopter;
import com.paw.model.Users;
import com.paw.service.AdopterService;
import com.paw.service.UsersService;


@RestController
public class AdopterController {

    private final AdopterService adopterService;
    private final UsersService UsersService;

    @Autowired
    public AdopterController(AdopterService adopterService,UsersService UsersService) {
        this.adopterService = adopterService;
		this.UsersService = UsersService;
    }

    @GetMapping
    public ResponseEntity<List<Adopter>> getAllAdopters() {
        List<Adopter> adopters = adopterService.findAll();
        return ResponseEntity.ok(adopters);
    }

    @GetMapping("adopters/{id}")
    public ResponseEntity<Adopter> getAdopterById(@PathVariable("id") Long id) {
        Adopter adopter = adopterService.findById(id);
        if (adopter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adopter);
    }

    @PostMapping("adopters_add")
    public ResponseEntity<Adopter> createAdopter(@RequestBody Adopter adopter) {
        Adopter createdAdopter = adopterService.save(adopter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdopter);
    }
    
    @PostMapping("adopters_add/{userId}")
    public ResponseEntity<Adopter> createAdopter(@PathVariable("userId") Long userId, @RequestBody Adopter adopter) {
        // Retrieve the user object based on the provided user ID
        Users user = UsersService.findById(userId);
        
        // Check if the user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Associate the adopter with the user
        adopter.setUsers(user);
        
        // Save the adopter
        Adopter createdAdopter = adopterService.save(adopter);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdopter);
    }
    @PutMapping("/adopters_update/{id}")
    public ResponseEntity<Adopter> updateAdopter(@PathVariable("id") Long id, @RequestBody Adopter adopter) {
        Adopter existingAdopter = adopterService.findById(id);
        if (existingAdopter == null) {
            return ResponseEntity.notFound().build();
        }

        // Retrieve the user object based on the existing user ID
        Users user = UsersService.findById(existingAdopter.getUsers().getId());

        // Update adopter details
        if (adopter.getName() != null) {
            existingAdopter.setName(adopter.getName());
        }
        // Update other fields as needed

        // Set the existing user back to the adopter object
        existingAdopter.setUsers(user);

        // Save the updated adopter
        Adopter updatedAdopter = adopterService.save(existingAdopter);

        return ResponseEntity.ok(updatedAdopter);
    }
    @DeleteMapping("adopters_delete/{id}")
    public ResponseEntity<Void> deleteAdopter(@PathVariable("id") Long id) {
        Adopter existingAdopter = adopterService.findById(id);
        if (existingAdopter == null) {
            return ResponseEntity.notFound().build();
        }
        adopterService.deleteById(id);
        return ResponseEntity.noContent().build();
       
    }
    
}
