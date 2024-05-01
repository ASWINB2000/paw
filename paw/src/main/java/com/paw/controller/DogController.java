package com.paw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.paw.model.Breed;
import com.paw.model.Dog;
import com.paw.model.User;
import com.paw.service.BreedService;
import com.paw.service.DogService;
import com.paw.service.UserService;

import jakarta.validation.Valid;

@Controller
public class DogController {

    private final DogService dogService;
    private final BreedService breedService;
    private final UserService userService;

    @Autowired
    public DogController(DogService dogService, BreedService breedService, UserService userService) {
        this.dogService = dogService;
        this.breedService = breedService;
        this.userService = userService;
    }

    @GetMapping("/dogs")
    public String getAllDogs(Model model) {
        List<Dog> dogs = dogService.getAllDogs();
        model.addAttribute("dogs", dogs);
        return "dogList"; // Assuming you have a view named dogList.html to display the list of dogs
    }

    @GetMapping("/dogs/{id}")
    public String getDogById(@PathVariable Long id, Model model) throws IOException {
        Dog dog = dogService.getDogById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found with id: " + id));
        model.addAttribute("dog", dog);
        return "dogDetails"; // Assuming you have a view named dogDetails.html to display dog details
    }
    
    @GetMapping("/selectBreed/{user_id}")
    public String selectBreed(@PathVariable Long user_id, Model model) {
        List<Breed> breeds = breedService.getAllBreeds();
        model.addAttribute("breeds", breeds);
        model.addAttribute("user_id", user_id);
        return "selectBreed"; // Assuming you have a view named selectBreed.html to select a breed
    }
    
    @GetMapping("/dog")
    public String addDogPage(@RequestParam("breed_id") Long breedId, @RequestParam("user_id") Long userId, Model model) {
        model.addAttribute("breedId", breedId);
        model.addAttribute("userId", userId);
        // Add any additional model attributes if needed
        return "dog"; 
    }


    @PostMapping("/dogs/{breed_id}/{user_id}")
    public String addDog(@Valid Dog dog, @PathVariable Long breed_id, @PathVariable Long user_id,
                         @RequestParam("image") MultipartFile file,
                         @RequestParam(value = "location", required = false) String location,
                         BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            List<Breed> breeds = breedService.getAllBreeds();
            model.addAttribute("breeds", breeds);
            model.addAttribute("user_id", user_id);
            return "selectBreed"; // Assuming you have a view named selectBreed.html to select a breed
        }
        Breed breed = breedService.getBreedbyId(breed_id)
                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Breed not found with id: " + breed_id));

        User user = userService.findByIdOrThrow(user_id);

        dog.setBreed(breed);
        dog.setUser(user);
        if (location != null) {
            dog.setLocation(location);
        }

        Dog savedDog = dogService.addDog(dog);
        String uploadImage = dogService.uploadImage(file, savedDog.getId());
        model.addAttribute("dog", savedDog);
        return "redirect:/user-page"; 
    }

    @PutMapping("/dogs/{id}")
    public String updateDog(@PathVariable Long id, @Valid Dog updatedDog, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            model.addAttribute("dog", updatedDog);
            return "editDogForm"; // Assuming you have a view named editDogForm.html to display the form for editing
        }
        Dog existingDog = dogService.getDogById(id)
                                     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found with id: " + id));

        existingDog.setName(updatedDog.getName());
        existingDog.setBirthDate(updatedDog.getBirthDate());
        existingDog.setGender(updatedDog.getGender());
        existingDog.setColour(updatedDog.getColour());
        existingDog.setDescription(updatedDog.getDescription());
        existingDog.setLocation(updatedDog.getLocation());

        if (updatedDog.getBreed() != null) {
            Breed updatedBreed = updatedDog.getBreed();
            if (updatedBreed.getId() != null) {
                Breed breed = breedService.getBreedbyId(updatedBreed.getId())
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Breed not found with id: " + updatedBreed.getId()));
                existingDog.setBreed(breed);
            } else {
                existingDog.setBreed(null);
            }
        }

        if (updatedDog.getUser() != null) {
            User updatedUser = updatedDog.getUser();
            if (updatedUser.getId() != null) {
                User user = userService.findByIdOrThrow(updatedUser.getId());
                existingDog.setUser(user);
            } else {
                existingDog.setUser(null);
            }
        }

        Dog savedDog = dogService.updateDog(id, existingDog);
        model.addAttribute("dog", savedDog);
        return "redirect:/dogs"; // Redirect to the dog list page after updating the dog
    }

    @DeleteMapping("/dogs/{id}")
    public String deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id);
        return "redirect:/dogs"; // Redirect to the dog list page after deleting the dog
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("errors", ex.getBindingResult().getAllErrors());
        return "addDogForm"; // Assuming you have a view named addDogForm.html to display the form
    }
}
