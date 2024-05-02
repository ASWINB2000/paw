package com.paw.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paw.model.Adopter;
import com.paw.model.User;
import com.paw.service.AdopterService;
import com.paw.service.UserService;


@RestController
public class AdopterController {

    private final AdopterService adopterService;
    private final UserService UserService;

    @Autowired
    public AdopterController(AdopterService adopterService,UserService UserService) {
        this.adopterService = adopterService;
		this.UserService = UserService;
    }

    @GetMapping("/adopters")
    public ResponseEntity<List<Adopter>> getAllAdopters() {
        List<Adopter> adopters = adopterService.findAll();
        return ResponseEntity.ok(adopters);
    }

    @GetMapping("/adopters/{id}")
    public ResponseEntity<Adopter> getAdopterById(@PathVariable("id") Long id) {
        Adopter adopter = adopterService.findById(id);
        if (adopter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adopter);
    }
    
    @GetMapping("/adopter/{userId}")
    public String adopter(@PathVariable("userId") Long userId, Model model) {
        // Pass the user ID to the adopter.html template
        model.addAttribute("userId", userId);
        return "adopter"; // Assuming "adopter.html" is located in the templates directory
    }

    
    
    
    @PostMapping("/adopters_add")
    public ResponseEntity<Adopter> createAdopter(@RequestBody Adopter adopter) {
        Adopter createdAdopter = adopterService.save(adopter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdopter);
    }
    
    @PostMapping("/adopters_add/{userId}")
    public ResponseEntity<Adopter> createAdopter(@PathVariable("userId") Long userId, @RequestBody Adopter adopter) {
        // Retrieve the user object based on the provided user ID
        User user = UserService.findById(userId);
        
        // Check if the user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Associate the adopter with the user
        adopter.setUser(user);
        
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
        User user = UserService.findById(existingAdopter.getUser().getId());

        // Update adopter details
        if (adopter.getName() != null) {
            existingAdopter.setName(adopter.getName());
        }
        // Update other fields as needed

        // Set the existing user back to the adopter object
        existingAdopter.setUser(user);

        // Save the updated adopter
        Adopter updatedAdopter = adopterService.save(existingAdopter);

        return ResponseEntity.ok(updatedAdopter);
    }
    @DeleteMapping("/adopters_delete/{id}")
    public ResponseEntity<Void> deleteAdopter(@PathVariable("id") Long id) {
        Adopter existingAdopter = adopterService.findById(id);
        if (existingAdopter == null) {
            return ResponseEntity.notFound().build();
        }
        adopterService.deleteById(id);
        return ResponseEntity.noContent().build();
       
    }
    
}
