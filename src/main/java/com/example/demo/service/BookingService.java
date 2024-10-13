package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Seat;
import com.example.demo.entity.User;
import com.example.demo.repo.BookingRepo;
import com.example.demo.repo.MovieRepo;
import com.example.demo.repo.SeatRepo;
import com.example.demo.repo.UserRepo;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepo bookingRepository;
    
    @Autowired
    private SeatRepo seatRepository;
    
    @Autowired
    private MovieRepo movieRepo;
    
    @Autowired
    private UserRepo userRepository;
    
    @Transactional
    public Booking bookSeats(Long userId, Long movieId, LocalDateTime showtime, List<Long> seatIds) throws Exception {
        System.out.println("user line 37 " + userId + movieId + showtime + seatIds);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new Exception("User not found"));
        
        System.out.println("user line 40 " + user.getEmail());

        Movie movie = movieRepo.findById(movieId)
            .orElseThrow(() -> new Exception("Movie not found"));
        
        
        System.out.println("user line 47 " + movie.getTitle());
        
        System.out.println("line 49" + seatIds);
        
        try {
        	List<Seat> seats = seatRepository.findBySeatNumberIn(seatIds);
        }catch (Exception e) {
        	System.out.println("line 54 " + e);
        }
        
        List<Seat> seats = seatRepository.findBySeatNumberIn(seatIds);
        System.out.println("user line 48 " + seats.size()+ seatIds.size() + "movieId here"+ movieId );
//        if (seats.size() != seatIds.size()) {
//            throw new Exception("Some seats not found");
//        }
        
        for (Seat s: seats) {
        	System.out.println("line 58" + s.getSeatNumber() + s.getMovie().getId());
        }
        
        List<Seat> filteredSeats = seats.stream()
        	    .filter(seat -> seat.getMovie().getId().equals(movieId))
        	    .collect(Collectors.toList());	
        
        for (Seat s: filteredSeats) {
        	System.out.println("line 66" + s.getSeatNumber() + s.getMovie().getId());
        } 
       
        // Step 4: Check if each seat is available for the given movie
        for (Seat seat : filteredSeats) {
        	System.out.println("seat.getIsBooked()+ seat.getMovie().getId() + movieId" + seat.getIsBooked()+ seat.getMovie().getId() + movieId);
            // Check if the seat belongs to the specified movie and if it is already booked
            if (seat.getMovie().getId().equals(movieId) && seat.getIsBooked()) {
                throw new Exception("Seat " + seat.getSeatNumber() + " is already booked for this movie.");
            }
        }


        for (Seat seat : filteredSeats) {
        	System.out.println("line 69" + movie.getId() + seat.getSeatNumber());
            seat.setIsBooked(true);
            seat.setMovie(movie); 
            seatRepository.save(seat);

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setMovie(movie);
            booking.setBookingTime(showtime);
            booking.setSeat(seat);
            bookingRepository.save(booking); 
        }
        return null;
    }
    
    
    public void addMovie(Movie movie) {
    	movieRepo.save(movie);
    }
    
    public void addSeatsForMovie(Movie movie) {
    	for(int i = 1;i <= 40; i++) {
    		Seat s = new Seat(i, false, movie);
    		seatRepository.save(s);
    	}
    }


	public List<Movie> getBookedMovie(Long userId) {
		List<Booking> book = bookingRepository.findByUserId(userId);

		List<Movie> movieIds = new ArrayList<>();;
		for(Booking b: book) {
			movieIds.add(b.getMovie());
		}
		return movieIds;
		
	}
    

}
