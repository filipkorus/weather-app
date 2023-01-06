package src;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import src.swing.JButtonTemplate;
import src.swing.JFrameTemplate;
import src.swing.JTextFieldTemplate;
import src.utils.HTTP;
import src.utils.Interval;
import src.utils.Datetime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class App {
	private final static Logger logger = Logger.getLogger(App.class);

	protected static JTextFieldTemplate humidityLabel, tempInLabel, tempOutLabel, dateLabel;
	protected static JFrameTemplate mainFrame;
	protected static JTextFieldTemplate humidityField, tempInField, tempOutField, dateField;
	protected static JButtonTemplate showChartsBtn;
	protected static Interval dataPollingInterval;
	public static void createAndShowGUI() {
		ActionListener actionListener = e -> {
			String pressedBtnLabel = e.getActionCommand();
			logger.info("Button \"" + pressedBtnLabel + "\" pressed");

			Object pressedBtn = e.getSource();

			if (pressedBtn.equals(showChartsBtn)) {
				Controller.handleShowChartsBtnClicked();
			}
		};

		/* application setup */
		mainFrame = new JFrameTemplate("Weather App", 420, 305, new ImageIcon("resources/icon.png"));

		/* create buttons */
		showChartsBtn = new JButtonTemplate("Show charts", new Rectangle(5, 210, 390, 50));

		/* add action listeners */
		showChartsBtn.addActionListener(actionListener);

		/* real-time data panel */
		JPanel realTimeDataPanel = new JPanel();
		realTimeDataPanel.setBounds(5,5,390,200);
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

		dateLabel = new JTextFieldTemplate("Refresh date", 16, JTextField.LEFT);
		dateField = new JTextFieldTemplate("date", 16, JTextField.RIGHT);
		realTimeDataPanel.add(dateLabel); realTimeDataPanel.add(dateField);

		/* add real-time data panel to main window */
		mainFrame.add(realTimeDataPanel);
//		realTimeDataPanel.setBackground(Color.red); // TODO: debug

		/* add buttons to main windows */
		mainFrame.add(showChartsBtn);

		/* set real-time data polling interval */
		dataPollingInterval = new Interval(() -> {
			JSONObject obj = HTTP.get("https://weather.fkor.us/api.php");
			if (obj != null) {
				humidityField.setText(String.format("%.2f", obj.getDouble("humidity")) + "%");
				tempInField.setText(String.format("%.2f", obj.getDouble("temperature_indoor")) + "°C");
				tempOutField.setText(String.format("%.2f", obj.getDouble("temperature_outdoor")) + "°C");

				dateField.setText(Datetime.howLongAgoFromNow(obj.getString("date")) + "s ago");

				if (!mainFrame.isVisible()) {
					/* show main window */
					mainFrame.setVisible(true);
					logger.info("App launched");
				}
			}
		}, 1000, "poll data every 1.5s");
		dataPollingInterval.start();
	}
}
