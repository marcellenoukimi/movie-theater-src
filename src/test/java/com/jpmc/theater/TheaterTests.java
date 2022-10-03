package com.jpmc.theater;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.time.LocalTime;

public class TheaterTests {
	@InjectMocks
	Theater theater = new Theater(LocalDateProvider.singleton());

	@Test
	void testTotalFeeForCustomer() {
		Theater theater = new Theater(LocalDateProvider.singleton());
		Customer john = new Customer("John Doe", "id-12345");
		Reservation reservation = theater.reserve(john, 2, 4);
		assertEquals(reservation.totalFee(), 37.5);
	}

	@Test
	void testPrintMovieSchedule() {
		theater.printSchedule();

	}

	@Test
	void testPrintMovieScheduleInJsonFormat() throws IOException, JSONException {
		theater.printScheduleInJsonFormat();
	}

	@Test
	void testReserve() {
		int sequence=222220;
		Throwable exception = Assertions.assertThrows(
				ArrayIndexOutOfBoundsException.class, () -> {
					theater.reserve(new Customer("Marcelle Noukimi", "id-12346"), sequence, 2);
				}
				);
		assertEquals("not able to find any showing for given sequence "+sequence, exception.getMessage());
	}

	@SuppressWarnings("static-access")
	@Test
	void testConvert24HourTimeTo12HourTime() {
		LocalTime lt1= LocalTime.of(23, 0);
		assertEquals(theater.convert24HourTimeTo12HourTime(lt1), "11:00 PM");

		LocalTime lt2= LocalTime.of(8, 30);
		assertEquals(theater.convert24HourTimeTo12HourTime(lt2), "08:30 AM");
	}
}
