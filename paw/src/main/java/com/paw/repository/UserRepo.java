package com.paw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paw.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	User findByEmail(String email);

	Optional<User> findById(Long id);
}