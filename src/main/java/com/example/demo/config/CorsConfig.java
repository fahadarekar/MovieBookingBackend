package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all 
                        .allowedOrigins("http://localhost:3000") //  origin (React, etc.)
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed methods
                        .allowedHeaders("*") // Allow any headers
                        .allowCredentials(true); // Allow credentials (cookies, etc.)
            }
        };
    }
}
