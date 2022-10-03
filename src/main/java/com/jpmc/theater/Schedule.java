package com.jpmc.theater;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Schedule { 
	private String startTime;
	private String title;
	private String runningTime;
	private String movieFee;

	@Override
	public String toString() {
		return "[Start Time: " + startTime + ", Movie Title: " + title
				+ ", Running Time: " + runningTime + ", Price: " + movieFee + "]";
	}
}
