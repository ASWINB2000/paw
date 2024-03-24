package com.paw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paw.model.Adopter;
import com.paw.model.Users;


@Repository
public interface UsersRepos extends JpaRepository<Users, Integer>{
    List<Adopter> findByUsernameContainsIgnoreCase(String name);
    Users findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Users> findById(long id);

}
