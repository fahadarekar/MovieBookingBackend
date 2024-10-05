package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	public UserRepo userRepo;
	
	public Optional<User> getUser(String email) {
		Optional<User> user= userRepo.findByEmail(email);
		if(user == null) {
			return null;
		}
		return user;
		
		
		
	}
	
	public void saveUser(User user) {
		userRepo.save(user);
    }

	
	
	
}
