package com.paw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.paw.model.Users;
import com.paw.repository.UsersRepos;

@Service
public class UsersService {
	private  UsersRepos usersRepository;


    public UsersService(UsersRepos usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public long count() {
        return usersRepository.count();
    }

    public void delete(Users users) {
        usersRepository.delete(users);
    }

    public void save(Users users) {
        usersRepository.save(users);
    }

    public Users findById(Long id) {
    	return usersRepository.findById(id).orElse(null);
//    	for (Users u : usersRepository.findAll()) {
//            if(u.getId().equals(id)) {
//                return u;
//            }
//        }
//        return null;
    }
    public Users findByIdOrThrow(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    // this is added so we can properly use @PostMapping("/dogs/{breed_id}/{user_id}") to throw errors while using postman

    public Users findUserByUsernameAndPassword(String username, String password) {
    	for (Users u : usersRepository.findAll()) {
            if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;

            }
        }
        return null;
    }
    
    
    public List<String> isValid(Users users) {
        List<String> notifications = new ArrayList<>();
        if (!usernameValidator(users.getUsername())) {
            notifications.add("The username must be between 4 and 20 characters");
        }
        if (!passwordValidator(users.getPassword())) {
            notifications.add("The password must be at least 4 characters and contain at least one number");
        }
        if (!emailValidator(users.getEmail())) {
            notifications.add("Wrong email format");
        }
        if (users.getFirstName() == null || users.getFirstName().isEmpty()) {
            notifications.add("The first name can't be blank");
        }
        if (users.getLastName() == null || users.getLastName().isEmpty()) {
            notifications.add("The last name can't be blank");
        }
        if (findByUsername(users.getUsername())) {
            notifications.add("The username already exists");
        }
        if (findByEmail(users.getEmail())) {
            notifications.add("There's already a user registered with this email");
        }
        return notifications;
    }

    public boolean findByUsername(String username) {
    	return usersRepository.existsByUsername(username);
//    	 for (Users u : usersRepository.findAll()) {
//             if(u.getUsername().equals(username)) {
//                 return true;
//             }
//         }
//         return false;
    }

    public boolean findByEmail(String email) {
    	return usersRepository.existsByEmail(email);
//        for (Users u : usersRepository.findAll()) {
//            if(u.getEmail().equals(email)) {
//                return true;
//            }
//        }
//        return false;
    }

    public static boolean usernameValidator(String username) {
        String pattern = ".{4,20}";
        return username.matches(pattern);
    }

    public static boolean passwordValidator(String password) {
        String pattern = "(?=.*[0-9])(?=\\S+$).{4,}";
        return password.matches(pattern);
    }

    public static boolean emailValidator(String email) {
        String pattern = "^(.+)@(.+)$";
        return email.matches(pattern);
    }
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class ResourceNotFoundException extends RuntimeException {
	    public ResourceNotFoundException(String message) {
	        super(message);
	    }

	}
}
