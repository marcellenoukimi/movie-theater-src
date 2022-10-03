package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
public class Movie {
	private static int MOVIE_CODE_SPECIAL = 1;

	@Getter
	private String title;

	@Getter
	private Duration runningTime;

	@Getter
	private double ticketPrice;

	private int specialCode;

	public double calculateTicketPrice(Showing showing) {
		return ticketPrice - getDiscount(showing.getSequenceOfTheDay(), showing);
	}

	private double getDiscount(int showSequence, Showing showing) throws RuntimeException{
		double discount=0.0;
		int count=0;
		ArrayList<Double> ar=new ArrayList<>();

		// 20% discount for special movie
		if (MOVIE_CODE_SPECIAL == specialCode) {
			count++;
			discount=ticketPrice * 0.2;
			ar.add(discount);
		}

		// $3 discount for the movie showing 1st of the day
		if (showSequence == 1) {
			count++;
			discount=3;
			ar.add(discount);

		} 

		// $2 discount for the movie showing 2nd of the day
		else if (showSequence == 2) {
			count++;
			discount=2;
			ar.add(discount);
		}

		// Any movies showing on Day 7 of a month, you'll get 1$ discount
		else if(showing.getMonthDay()==7) {
			count++;
			discount=1;
			ar.add(discount);
		}

		// Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
		if(showing.getStartTime().isAfter(LocalTime.of(10, 59)) && showing.getStartTime().isBefore(LocalTime.of(16, 01))) {
			count++;
			discount= ticketPrice * 0.25;
			ar.add(discount);
		}

		return count >1 ? Collections.max(ar): discount;
	}
}