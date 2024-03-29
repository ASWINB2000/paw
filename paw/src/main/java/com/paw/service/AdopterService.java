package com.paw.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.paw.model.Adopter;
import com.paw.repository.AdopterRepos;
import java.util.List;

@Service
public class AdopterService {
	private final AdopterRepos adopterRepository;

	@Autowired
    public AdopterService(AdopterRepos adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    public List<Adopter> findAll() {
        return adopterRepository.findAll();
    }

    public Adopter findById(Long id) {
        return adopterRepository.findById(id).orElse(null);
    }

    public Adopter save(Adopter adopter) {
        return adopterRepository.save(adopter);
    }

    public void deleteById(Long id) {
        adopterRepository.deleteById(id);
    }
}
