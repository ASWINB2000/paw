package com.paw.controller;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paw.dto.UserDto;
import com.paw.service.UserService;


@Controller
public class UserController {
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService ser;
	
	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}
	
	@PostMapping("/registration")
		public String saveUser(@ModelAttribute("user") UserDto userDto,Model model)
		{
			ser.save(userDto);
			model.addAttribute("message", "Register successfully");
			return "register";
		}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user-page")
	public String userPage(Model model,Principal principal) 
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "user";
	}
	
	@GetMapping("/admin-page")
	public String adminPage(Model model,Principal principal) 
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "admin";
	}


}
