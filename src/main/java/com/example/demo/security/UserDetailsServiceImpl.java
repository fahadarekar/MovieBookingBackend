// src/main/java/com/example/demo/security/UserDetailsServiceImpl.java
package com.example.demo.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) 
    		throws UsernameNotFoundException {
        com.example.demo.entity.User user = userRepository.findByEmail(username)
        		.orElseThrow(() -> 
        			new UsernameNotFoundException("User Not Found with username: " + username));

        return org.springframework.security.core.userdetails.User
        		.withUsername(user.getName())
        		.password(user.getPassword())
        		.authorities(new ArrayList<>())
        		.build();
    }
}
