package utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * a set of datetime parsing methods
 */
public class Datetime {
	/**
	 * parses date from String (in format yyyy-MM-dd HH:mm:ss) to LocalDateTime
	 * @param datetimeString date (as String) in format yyyy-MM-dd HH:mm:ss
	 * @return LocalDateTime object with parsed date
	 */
	public static LocalDateTime fromString(String datetimeString) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.from(f.parse(datetimeString));
	}

	/**
	 * measures how many seconds passed since given datetime
	 *
	 * @param datetime datetime since we measure how many seconds passed from
	 * @return String with seconds showing how many seconds passed from given date
	 */
	public static String howLongAgoFromNow(LocalDateTime datetime) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(now, datetime);

		return ""+duration.getSeconds();
	}

	/**
	 * measures how many seconds passed since given datetime
	 *
	 * @param datetimeString datetime in String (in format yyyy-MM-dd HH:mm:ss) since we measure how many seconds passed from
	 * @return String with seconds showing how many seconds passed from given date
	 */
	public static String howLongAgoFromNow(String datetimeString) {
		LocalDateTime datetime = Datetime.fromString(datetimeString);

		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(datetime, now);

		return ""+duration.getSeconds();
	}

	/**
	 * parses date
	 * @param localDate LocalDate object to parse date
	 * @return String with date in format `12 January 2023`
	 */
	public static String getDateString(LocalDate localDate) {
		String monthName = capitalize(localDate.getMonth().toString());
		String dayNumber = "" + localDate.getDayOfMonth();
		String yearNumber = "" + localDate.getYear();

		return dayNumber + " " + monthName + " " + yearNumber;
	}

	/**
	 * capitalizes string
	 * @param str string to capitalize
	 * @return capitalized string
	 */
	private static String capitalize(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
