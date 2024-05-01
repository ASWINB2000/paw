package com.paw.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.paw.model.Dog;
import com.paw.repository.DogRepos;
@Service
public class DogService {
	@Autowired
	private final  DogRepos dogRepository;
    @Autowired
    public DogService(DogRepos dogRepository) {
        this.dogRepository = dogRepository;
    }
	private final String FOLDER_PATH="C:/Users/Aswin/Desktop/Quest/SpringBoot/paw/paw/images/";


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

	public Optional<Dog> findById(Long dogId) {
		return dogRepository.findById(dogId);
	}
	
	public String uploadImage(MultipartFile file, Long dogId)throws IOException{
		String filePath=FOLDER_PATH+file.getOriginalFilename();
		Dog existingDog = dogRepository.findById(dogId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found with id: " + dogId));
		existingDog.setFilePath(filePath);
		existingDog.setType(file.getContentType()); 
		Dog savedDog = dogRepository.save(existingDog);
		file.transferTo(new File(filePath));
		if (savedDog != null) {
		return "file uploaded successfully : " + filePath;
		}
		return null;
	}
	
	public byte[] downloadImage(String fileName)throws IOException{
		Optional<Dog> fileData=dogRepository.findByName(fileName);
		String filePath=fileData.get().getFilePath();
		byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
		
	}
	
	
	
	
}
