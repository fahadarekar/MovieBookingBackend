package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Seat;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Long> {
	
	List<Seat> findBySeatNumberIn(List<Long> seatIds);
}
