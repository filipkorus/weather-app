package src;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import src.swing.JButtonTemplate;
import src.swing.JFrameTemplate;
import src.swing.JTextFieldTemplate;

import src.utils.Database;
import src.utils.HTTP;
import src.utils.Interval;
import src.utils.Datetime;

public class App {
	private final static Logger logger = Logger.getLogger(App.class);

	protected static JTextFieldTemplate realTimeDataPanelLabel, humidityLabel, tempInLabel, tempOutLabel, refreshedAgoLabel, navigationPanelLabel, graphTitleLabel;
	protected static LocalDate currentDay = LocalDate.now();
	protected static JFrameTemplate mainWindow;
	protected static JTextFieldTemplate humidityField, tempInField, tempOutField, refreshedAgoField;
	protected static JButtonTemplate showGraphBtn, previousDayBtn, nextDayBtn;
	protected static Interval dataPollingInterval;
	protected final static ImageIcon icon = new ImageIcon("resources/icon.png");
	protected static final Database db = new Database();
	public static void createAndShowGUI() {
		ActionListener actionListener = e -> {
			String pressedBtnLabel = e.getActionCommand();
			logger.info("Button \"" + pressedBtnLabel + "\" pressed");

			Object pressedBtn = e.getSource();

			if (pressedBtn.equals(showGraphBtn)) {
				Controller.handleShowChartsBtnClicked();
			} else if (pressedBtn.equals(previousDayBtn)) {
				Controller.handlePreviousDayBtnClicked();
			} else if (pressedBtn.equals(nextDayBtn)) {
				Controller.handleNextDayBtnClicked();
			}
		};

		/* application setup */
		mainWindow = new JFrameTemplate("Weather App", 500, 445, icon);

		/* real-time data panel */
		realTimeDataPanelLabel = new JTextFieldTemplate("Real-time data from Arduino", 16, JTextField.CENTER);
		realTimeDataPanelLabel.setBounds(new Rectangle(5, 5, 475, 40));
		mainWindow.add(realTimeDataPanelLabel);

		JPanel realTimeDataPanel = new JPanel();
		realTimeDataPanel.setBounds(new Rectangle(5,50,475,200));
		realTimeDataPanel.setLayout(new GridLayout(4,2,20, 5));

		tempInLabel = new JTextFieldTemplate("Indoor temp.", 16, JTextField.LEFT);
		tempInField = new JTextFieldTemplate("indoor temperature", 16, JTextField.RIGHT);
		realTimeDataPanel.add(tempInLabel); realTimeDataPanel.add(tempInField);

		tempOutLabel = new JTextFieldTemplate("Outdoor temp.", 16, JTextField.LEFT);
		tempOutField = new JTextFieldTemplate("outdoor temperature", 16, JTextField.RIGHT);
		realTimeDataPanel.add(tempOutLabel); realTimeDataPanel.add(tempOutField);

		humidityLabel = new JTextFieldTemplate("Indoor humidity", 16, JTextField.LEFT);
		humidityField = new JTextFieldTemplate("humidity", 16, JTextField.RIGHT);
		realTimeDataPanel.add(humidityLabel); realTimeDataPanel.add(humidityField);

		refreshedAgoLabel = new JTextFieldTemplate("Refreshed", 16, JTextField.LEFT);
		refreshedAgoField = new JTextFieldTemplate("date", 16, JTextField.RIGHT);
		realTimeDataPanel.add(refreshedAgoLabel); realTimeDataPanel.add(refreshedAgoField);

		/* add real-time data panel to main window */
		mainWindow.add(realTimeDataPanel);

		JSeparator sep = new JSeparator();
		sep.setBounds(new Rectangle(5, 260, 475, 10));
		mainWindow.add(sep);

		/* graph panel */
		navigationPanelLabel = new JTextFieldTemplate("Choose a date to generate a graph", 16, JTextField.CENTER);
		navigationPanelLabel.setBounds(new Rectangle(5, 270, 475, 40));
		mainWindow.add(navigationPanelLabel);

		previousDayBtn = new JButtonTemplate("⬅️");
		graphTitleLabel = new JTextFieldTemplate(Datetime.getDateString(currentDay), 16, JTextField.CENTER, false);
		nextDayBtn = new JButtonTemplate("➡️");

		nextDayBtn.setEnabled(false); // next day button must be disabled by default

		/* add actions listeners */
		previousDayBtn.addActionListener(actionListener);
		nextDayBtn.addActionListener(actionListener);

		JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(new GridLayout(1,3,5,5));
		navigationPanel.setBounds(new Rectangle(5, 315, 475, 40));
		navigationPanel.add(previousDayBtn); navigationPanel.add(graphTitleLabel); navigationPanel.add(nextDayBtn);

		mainWindow.add(navigationPanel);

		showGraphBtn = new JButtonTemplate("Show graph", new Rectangle(5, 360, 475, 40));
		showGraphBtn.addActionListener(actionListener);
		mainWindow.add(showGraphBtn);

		/* real-time data polling interval */
		dataPollingInterval = new Interval(() -> {
			JSONObject obj = HTTP.get("https://weather.fkor.us/api.php");
			if (obj != null) {
				humidityField.setText(String.format("%.2f", obj.getDouble("humidity")) + "%");
				tempInField.setText(String.format("%.2f", obj.getDouble("temperature_indoor")) + "°C");
				tempOutField.setText(String.format("%.2f", obj.getDouble("temperature_outdoor")) + "°C");

				refreshedAgoField.setText(Datetime.howLongAgoFromNow(obj.getString("date")) + "s ago");

				if (!mainWindow.isVisible()) {
					/* show main window */
					mainWindow.setVisible(true);
					logger.info("App launched");
				}
			}
		}, 1000, "poll data every 1s");
		dataPollingInterval.start();
	}
}
