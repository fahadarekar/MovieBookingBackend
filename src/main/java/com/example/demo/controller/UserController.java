package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@PostMapping("/signin")
	public ResponseEntity<String> Login(@RequestBody User user){
		
		Optional<User> user1 = userService.getUser(user.getEmail());
		
		if (!user1.isPresent()) {
			 return ResponseEntity.status(401).body("Invalid username or password");
		}
		
		User exUser = user1.get();
		if (!exUser.getPassword().equals(user.getPassword())) {
			// Passwords don't match, return 401 Unauthorized
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
		
		// If email and password match, return success response
		return ResponseEntity.ok("Login successful");
	}
	
	@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
		System.out.println("inside register");
        Optional<User> existingUser = userService.getUser(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
	

}
