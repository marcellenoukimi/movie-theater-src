package com.jpmc.theater;


import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.*;

@AllArgsConstructor
@Getter
@Component
public class Showing {
	private Movie movie;
	private int sequenceOfTheDay;
	private LocalDateTime showStartTime;

	public LocalTime getStartTime() {
		return LocalTime.of(showStartTime.getHour(),showStartTime.getMinute());
	}
	public int getMonthDay() {
		return showStartTime.getDayOfMonth();
	}
	public boolean isSequence(int sequence) {
		return this.sequenceOfTheDay == sequence;
	}

	public double getMovieFee() {
		return movie.calculateTicketPrice(this);
	}
}
