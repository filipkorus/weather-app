package src.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Datetime {
	public static LocalDateTime fromString(String datetimeString) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime datetime = LocalDateTime.from(f.parse(datetimeString));

		return datetime;
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
}
