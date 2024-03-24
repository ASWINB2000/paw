package com.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paw.model.Adoption;
import com.paw.repository.AdoptionRepos;

@Service
public class AdoptionService {
	
	private  AdoptionRepos adoptionRepository;
//	@Autowired
//	public AdoptionService(AdoptionRepos adoptionRepository) {
//		this.adoptionRepository=adoptionRepository;
//	}
	
	public List<Adoption> findAll() {
        return adoptionRepository.findAll();
    }
	
    public Adoption add(Adoption adoption) {
        return adoptionRepository.save(adoption);
    }

    public Adoption update(Adoption adoption) {
        return adoptionRepository.save(adoption);
    }

    public void delete(Long id) {
        adoptionRepository.deleteById(id);
    }
}
