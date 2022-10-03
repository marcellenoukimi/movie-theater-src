package com.jpmc.theater;

import java.time.LocalDate;


// Singleton class with lazy initialization
public class LocalDateProvider {
	// Private constructor
	private LocalDateProvider() {}

	// Static field containing its only instance
	private static LocalDateProvider instance ;

	// Static factory method for obtaining the instance
	public static LocalDateProvider singleton() {
		if (instance == null) {
			instance = new LocalDateProvider();
		}
		return instance;
	}

	public LocalDate currentDate() {
		return LocalDate.now();
	}
}

