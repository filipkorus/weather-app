import org.apache.log4j.Logger;

import utils.DatabaseResults;
import utils.Datetime;

import javax.swing.*;
import java.time.LocalDate;

/**
 * app events' handler
 */
public class Controller extends App {
	/**
	 * logger for Controller class
	 */
	private final static Logger logger = Logger.getLogger(Controller.class);

	/**
	 * "show graph" button click handler: gets dataset from database and creates graph, all of these in a separate Thread
	 */
	protected static void handleShowGraphBtnClick() {
		String windowTitle = "Outdoor temperatures on " + Datetime.getDateString(currentDay);

		new Thread(() -> {
			showGraphBtn.setEnabled(false);

			DatabaseResults dr = db.createDataset(currentDay);

			showGraphBtn.setEnabled(true);

			if (dr.responseCode() == 404) {
				JOptionPane.showMessageDialog(mainWindow,
						  "There is no dataset in the database from " + Datetime.getDateString(currentDay) + ".",
						  "No dataset",
						  JOptionPane.WARNING_MESSAGE);

				return;
			}

			if (dr.responseCode() == 500) {
				JOptionPane.showMessageDialog(mainWindow,
						  "Unexpected database error occurred :(",
						  "Error",
						  JOptionPane.ERROR_MESSAGE);

				return;
			}

			new Graph(
					  windowTitle,
					  "Time", "Temperature [°C]",
					  dr.dataset(), icon
			).show();
		}).start();
	}

	/**
	 * "previous day" button click handler
	 */
	protected static void handlePreviousDayBtnClick() {
		currentDay = currentDay.plusDays(-1);
		graphTitleLabel.setText(Datetime.getDateString(currentDay));

		if (!currentDay.equals(LocalDate.now())) {
			nextDayBtn.setEnabled(true);
		}
	}

	/**
	 * "next day" button click handler
	 */
	protected static void handleNextDayBtnClick() {
		if (currentDay.plusDays(1).equals(LocalDate.now())) {
			nextDayBtn.setEnabled(false);
		}

		currentDay = currentDay.plusDays(1);
		graphTitleLabel.setText(Datetime.getDateString(currentDay));
	}
}
