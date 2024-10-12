// src/main/java/com/example/demo/controller/AuthController.java
package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepository;
    
    @Autowired
    BookingService bookingService;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received login request for user: " + loginRequest.getUsername());

        // Validate username and password fields
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username is required"));
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
        }

        System.out.println(loginRequest.getUsername() + loginRequest.getPassword());

        try {
            // Retrieve user details from the database
            User user = userRepository.findByEmail(loginRequest.getUsername())
                                      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Check if the password from the request matches the encoded password in the database
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Return 401 if the password does not match
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
            }

            // Generate JWT token for the authenticated user
            String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

            System.out.println("Authenticated user email: " + user.getEmail());

            // Prepare the response with user ID and JWT token
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userId", user.getId()); // Storing userId as an integer
            responseBody.put("token", jwt);

            // Return the response in the response entity
            return ResponseEntity.ok(responseBody); // Return the JSON object with HTTP 200 status

        } catch (BadCredentialsException e) {
            // Handle invalid username/password
            System.out.println("Invalid username or password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        } catch (UsernameNotFoundException e) {
            // Handle user not found case
            System.out.println("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        } catch (Exception e) {
            // Handle any other exceptions
            System.out.println("Authentication error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Authentication error"));
        }
    }


    @PostMapping("/addMovie")
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
		
		bookingService.addMovie(movie);
		bookingService.addSeatsForMovie(movie);
		return null;
	}


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest signUpRequest) {
        try {
            userService.registerUser(signUpRequest);
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity
            		.badRequest()
            		.body(e.getMessage());
        }
    }
}
