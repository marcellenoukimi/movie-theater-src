package com.jpmc.theater;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reservation {
	private Customer customer;
	private Showing showing;
	private int audienceCount;

	public double totalFee() {
		return showing.getMovieFee() * (double) audienceCount;
	}
}