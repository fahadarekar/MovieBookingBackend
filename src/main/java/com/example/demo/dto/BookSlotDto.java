package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookSlotDto {
	
	public Long userId;
	
	public LocalDateTime showTime;
	
	public List<Long> seatIds;
	
	public Long movie_id;

	public Long getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(Long movie_id) {
		this.movie_id = movie_id;
	}

	public Long getId() {
		return userId;
	}

	public void setId(Long user_id) {
		this.userId = user_id;
	}

	public LocalDateTime getShowTime() {
		return showTime;
	}

	public void setShowTime(LocalDateTime showTime) {
		this.showTime = showTime;
	}

	public List<Long> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<Long> seatIds) {
		this.seatIds = seatIds;
	}
	
}
