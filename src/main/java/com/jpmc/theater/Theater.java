package com.jpmc.theater;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

public class Theater {
	@Autowired
	LocalDateProvider provider;

	@Autowired
	Showing showing;

	private List<Showing> schedule;

	public Theater(LocalDateProvider provider) {
		this.provider = provider;
		Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
		Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
		Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
		schedule = List.of(
				new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
				new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
				new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
				new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
				new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
				new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
				new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
				new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
				new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
				);
	}

	public Reservation reserve(Customer customer, int sequence, int howManyTickets){     
		try {
			showing = schedule.get(sequence - 1);
		} catch (ArrayIndexOutOfBoundsException ex) {
			throw new ArrayIndexOutOfBoundsException("not able to find any showing for given sequence " + sequence);
		}
		return new Reservation(customer, showing, howManyTickets);
	}

	public void printSchedule() {
		System.out.println("Date: "+provider.currentDate());
		System.out.println("\nMovie Schedule in Simple Text Format");
		System.out.println("===================================================");
		schedule.forEach(s ->
		System.out.println( s.getSequenceOfTheDay() + ": " + convert24HourTimeTo12HourTime( s.getStartTime())+ " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee()));
		System.out.println("===================================================");
	}

	public void printScheduleInJsonFormat() throws IOException, JSONException{
		System.out.println("\nMovie Schedule in JSON Format");
		JSONObject jsonProviderObject = new JSONObject();
		for(int i=0; i<schedule.size();i++) {
			jsonProviderObject.put(""+schedule.get(i).getSequenceOfTheDay(), 
					new Schedule(convert24HourTimeTo12HourTime(schedule.get(i).getStartTime()) , 
							schedule.get(i).getMovie().getTitle(), humanReadableFormatForJsonFormat(schedule.get(i).getMovie().getRunningTime()),
							" $" + schedule.get(i).getMovieFee()
							));
		}
		Stream.of(jsonProviderObject.toString()).forEach(System.out::println);
	}

	public String humanReadableFormat(Duration duration) {
		long hour = duration.toHours();
		long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

		return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
	}

	public String humanReadableFormatForJsonFormat(Duration duration) {
		long hour = duration.toHours();
		long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

		return String.format("%s hour%s %s minute%s", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
	}

	// Post-fix (s) should be added to handle plural correctly
	private String handlePlural(long value) {
		return value == 1 ? "" : "s";
	}

	public static String convert24HourTimeTo12HourTime(LocalTime l){
		String time=String.valueOf(l);
		String format;

		// Parsing hours, minutes and seconds in a String array
		String[] arr = time.split(":");

		// Converting hours into integer value
		int hh = Integer.parseInt(arr[0]);

		if (hh > 12) {
			hh = hh - 12;
			format = "PM";
		}
		else if (hh == 00) {
			hh = 12;
			format = "AM";
		}
		else if (hh == 12) {
			hh = 12;
			format = "PM";
		}
		else {
			format = "AM";
		}

		String hour = String.format("%02d", hh);
		String minute = arr[1];

		return hour + ":" + minute + " " + format;
	}
}
