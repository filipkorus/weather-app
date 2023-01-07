package src;

import org.apache.log4j.Logger;
import src.utils.Datetime;

import java.time.LocalDate;

public class Controller extends App {
	private final static Logger logger = Logger.getLogger(Controller.class);
	protected static void handleShowChartsBtnClicked() {
		createGraph();
	}

	public static void handlePreviousDayBtnClicked() {
		currentDay = currentDay.plusDays(-1);
		graphTitleLabel.setText(Datetime.getDateString(currentDay));

		if (!currentDay.equals(LocalDate.now())) {
			nextDayBtn.setEnabled(true);
		}
	}

	public static void handleNextDayBtnClicked() {
		if (currentDay.plusDays(1).equals(LocalDate.now())) {
			nextDayBtn.setEnabled(false);
		}

		currentDay = currentDay.plusDays(1);
		graphTitleLabel.setText(Datetime.getDateString(currentDay));
	}
}
