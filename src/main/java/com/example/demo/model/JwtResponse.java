package com.example.demo.model;

//JWT Token Response Model
public class JwtResponse {

 private final String jwtToken;

 public JwtResponse(String jwtToken) {
     this.jwtToken = jwtToken;
 }

 public String getJwtToken() {
     return jwtToken;
 }
}

