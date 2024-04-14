package com.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.paw.dto.UserDto;
import com.paw.model.User;
import com.paw.repository.UserRepo;




@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo rep;
	private static final String DEFAULT_ROLE="USER";
	@Override
	public User save(UserDto userDto) {
		 String role = userDto.getRole();
	        if (role == null || role.isEmpty()) {
	            role = DEFAULT_ROLE;
	        }
		User user=new User(userDto.getEmail(),userDto.getUsername() ,passwordEncoder.encode(userDto.getPassword()) ,role,userDto.getFirst_name(),userDto.getLast_name());
		return rep.save(user);
	}
	@Override
	public User findById(Long id) {
		return rep.findById(id).orElse(null);
	}
	
	@Override
	public User findByIdOrThrow(Long user_id) {
		 return rep.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user_id));
	   
	}
	  @ResponseStatus(value = HttpStatus.NOT_FOUND)
	  public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) 
		{
			super(message);
		}
	

	  }
}
