package com.example.demo.config;
import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class KeyGenerator {
    public static void main(String[] args) {
    	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    	String secretString = Base64.getEncoder().encodeToString(key.getEncoded());
    	System.out.println("Use this secret key: " + secretString);
    }
}
