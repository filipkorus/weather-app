package src.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Datetime {
	public static LocalDateTime fromString(String datetimeString) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.from(f.parse(datetimeString));
	}

	public static String howLongAgoFromNow(LocalDateTime datetime) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(now, datetime);

		return ""+duration.getSeconds();
	}

	public static String howLongAgoFromNow(String datetimeString) {
		LocalDateTime datetime = Datetime.fromString(datetimeString);

		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(datetime, now);

		return ""+duration.getSeconds();
	}

	public static String getDateString(LocalDate localDate) {
		String monthName = capitalize(localDate.getMonth().toString());
		String dayNumber = "" + localDate.getDayOfMonth();
		String yearNumber = "" + localDate.getYear();

		return dayNumber + " " + monthName + " " + yearNumber;
	}

	private static String capitalize(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
