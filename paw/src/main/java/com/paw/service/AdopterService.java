package com.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paw.model.Adopter;
import com.paw.model.Users;
import com.paw.repository.AdopterRepos;
import com.paw.repository.UsersRepos;

import java.util.List;
import java.util.Optional;

@Service
public class AdopterService {

    private final AdopterRepos adopterRepository;
    private final UsersRepos userRepository;

    @Autowired
    public AdopterService(AdopterRepos adopterRepository, UsersRepos userRepository) {
        this.adopterRepository = adopterRepository;
        this.userRepository = userRepository;
    }

    public List<Adopter> findAll() {
        return adopterRepository.findAll();
    }

    public Optional<Adopter> findById(Long id) {
        return adopterRepository.findById(id);
    }

    @Transactional
    public Adopter save(Long userId, Adopter adopter) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            adopter.setUsers(user);
            return adopterRepository.save(adopter);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public void deleteById(Long id) {
        adopterRepository.deleteById(id);
    }
}
