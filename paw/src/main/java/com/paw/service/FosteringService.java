package com.paw.service;

import com.paw.model.Adopter;
import com.paw.model.Fostering;
import com.paw.repository.AdopterRepos;
import com.paw.repository.FosteringRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FosteringService {
    private final FosteringRepos fosteringRepository;
    private final AdopterRepos adopterRepository;

    @Autowired
    public FosteringService(FosteringRepos fosteringRepository, AdopterRepos adopterRepository) {
        this.fosteringRepository = fosteringRepository;
		this.adopterRepository = adopterRepository;
    }

    public List<Fostering> findAll() {
        return fosteringRepository.findAll();
    }

    public Fostering add(Fostering fostering) {
        return fosteringRepository.save(fostering);
    }

    public void delete(Long id) {
        fosteringRepository.deleteById(id);
    }

    public List<Fostering> findByAdopterNameContainsIgnoreCase(String name) {
        return fosteringRepository.findByAdopter_NameContainsIgnoreCase(name);
    }

   
    public Optional<Adopter> findById(Long id) {
        return adopterRepository.findById(id);
    }
}
