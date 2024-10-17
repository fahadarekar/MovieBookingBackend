package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookSlotDto;
import com.example.demo.entity.Movie;
import com.example.demo.repo.MovieRepo;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:3001")
public class BookingController {
	
	@Autowired
	public BookingService bookingService;
	
	@Autowired
	public MovieRepo movieRepo;

	@PostMapping("/book")
	public ResponseEntity<String> bookSlot(@RequestBody BookSlotDto bookSlotDto){
		System.out.println("Added service class");
		try {
			bookingService.bookSeats(bookSlotDto.getId(),bookSlotDto.getMovie_id(), bookSlotDto.getShowTime(), bookSlotDto.getSeatIds());
			
		}catch(Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Exception Found");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Seat Booked");
	}
	
	
	
	
	
	@PostMapping("/bookedMovie")
	public ResponseEntity<List<Movie>> getBookedMovie(@RequestParam Long userId){
		System.out.println("line 57 book API" + userId);
		List<Movie> bookedMovies = bookingService.getBookedMovie(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(bookedMovies);
	}
	
	@GetMapping("/movie/ids")
    public ResponseEntity<List<Long>> getMovieIds() {
		System.out.println("Added service classfghujijhg");
        // Get the list of movies and extract their IDs
        List<Long> movieIds = movieRepo.findAll()
                                       .stream()
                                       .map(Movie::getId) // Assuming 'getId()' is the method to get the movie ID
                                       .collect(Collectors.toList());
        	
        // Return the list of movie IDs in the response
        return ResponseEntity.status(HttpStatus.OK).body(movieIds);
    }
	
}