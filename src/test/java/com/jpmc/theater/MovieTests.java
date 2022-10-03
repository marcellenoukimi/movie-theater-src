package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
	Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
	Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
	Movie theBatMan2 = new Movie("The Batman", Duration.ofMinutes(95), 9, 1);

	@Test
	void testSpecialMovieWith20PercentDiscount() {
		Showing showing1= new Showing(theBatMan2, 3, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(10, 00)));
		assertEquals(7.2, theBatMan2.calculateTicketPrice(showing1));

		Showing showing2 = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(8, 45)));
		assertEquals(10.0, spiderMan.calculateTicketPrice(showing2));
	}

	@Test
	void testMovieShowingFirstOfTheDay() {
		Showing showing3= new Showing(theBatMan, 1, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(10, 50)));
		assertEquals(6.0, theBatMan.calculateTicketPrice(showing3));
	}
	@Test
	void testMovieShowingSecondOfTheDay() {
		Showing showing4= new Showing(theBatMan, 2, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(10, 50)));
		assertEquals(7.0, theBatMan.calculateTicketPrice(showing4));
	}
	@Test
	void testMoviesShowingStartingBetween11AmAnd4Pm() {
		Showing showing5= new Showing(theBatMan, 3, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(12, 50)));
		assertEquals(6.75, theBatMan.calculateTicketPrice(showing5));
	}
	@Test
	void testMoviesShowingOnDay7OfAMonth() {
		Showing showing6= new Showing(theBatMan, 7, LocalDateTime.of(LocalDate.of(2022, 10, 7), LocalTime.of(10, 50)));
		assertEquals(8.0, theBatMan.calculateTicketPrice(showing6));
	}
	@Test
	void testMoviesWhenMultiplesRulesAreMet() {
		// Special movie and movie showing first; apply the biggest discount amount
		Showing showing7= new Showing(theBatMan2, 1, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(10, 00)));
		assertEquals(6.0, theBatMan2.calculateTicketPrice(showing7));

		// Movies showing starting between 11AM ~ 4pm and the movie showing 1st of the day, return the price with the biggest discount applied
		Showing showing8= new Showing(theBatMan, 1, LocalDateTime.of(LocalDate.of(2022, 10, 3), LocalTime.of(11, 00)));
		assertEquals(6.0, theBatMan.calculateTicketPrice(showing8));

		// Movies showing starting between 11AM ~ 4pm and movies showing on 7th, return the price with the biggest discount applied
		Showing showing9= new Showing(theBatMan, 3, LocalDateTime.of(LocalDate.of(2022, 11, 7), LocalTime.of(12, 50)));
		assertEquals(6.75, theBatMan.calculateTicketPrice(showing9));

		// Movies showing starting between 11AM ~ 4pm and movies showing on 7th and movies showing first of the day, return the price with the biggest discount applied
		Showing showing10= new Showing(theBatMan, 1, LocalDateTime.of(LocalDate.of(2022, 11, 7), LocalTime.of(12, 50)));
		assertEquals(6.0, theBatMan.calculateTicketPrice(showing10));

		// Movies showing starting between 11AM ~ 4pm and movies showing on 7th and movies showing second of the day, return the price with the biggest discount applied
		Showing showing11= new Showing(theBatMan, 2, LocalDateTime.of(LocalDate.of(2022, 10, 7), LocalTime.of(10, 50)));
		assertEquals(7.0, theBatMan.calculateTicketPrice(showing11));		
	}
}
