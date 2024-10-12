// src/main/java/com/example/demo/payload/JwtResponse.java
package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    
	public JwtResponse(String token, Long id) {
		super();
		this.token = token;
		this.id = id;
	}
	private String token;
    private Long id;
    private String error;
	public JwtResponse(String error) {
		super();
		this.error = error;
	}
}
