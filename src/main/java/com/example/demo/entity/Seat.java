package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Seats")
public class Seat {
    
    public Seat(Long id, int seatNumber, Boolean isBooked, Movie movie, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.seatNumber = seatNumber;
		this.isBooked = isBooked;
		this.movie = movie;
		this.createdAt = createdAt;
	}

	public Seat() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatNumber;

    public Seat(int seatNumber, Boolean isBooked, Movie movie) {
		super();
		this.seatNumber = seatNumber;
		this.isBooked = isBooked;
		this.movie = movie;
	}

	private Boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Getters and Setters
}
