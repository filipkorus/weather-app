package src;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import src.utils.HTTP;
import src.utils.Interval;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class App {
	private final static Logger logger = Logger.getLogger(App.class);
	protected static JTextField dateField;
	protected static JButton stopPollingBtn, interruptPollingBtn;
	protected static Interval dataPollingInterval;
	public static void createAndShowGUI() {
		ActionListener actionListener = e -> {
			String pressedBtnLabel = e.getActionCommand();
			logger.info("Button \"" + pressedBtnLabel + "\" pressed");

			Object pressedBtn = e.getSource();

			if (pressedBtn.equals(stopPollingBtn)) {
				Controller.handleStopPollingBtnClicked();
			} else if (pressedBtn.equals(interruptPollingBtn)) {
				dataPollingInterval.interrupt();
			}
		};

		// application setup
		JFrame jf = new JFrame("Weather App");
		jf.setIconImage(new ImageIcon("resources/icon.png").getImage());
		jf.setResizable(false);
		jf.setLayout(null);
		jf.setSize(660, 400);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel jp = new JPanel();

		// create buttons
		stopPollingBtn = new JButton("Stop polling data");
		interruptPollingBtn = new JButton("Interrupt polling data");

		// add action listeners
		stopPollingBtn.addActionListener(actionListener);
		interruptPollingBtn.addActionListener(actionListener);

		jp.setBounds(5,50,390,300);
//		jp.setLayout(new GridLayout(5,4,5,5));
		jp.add(stopPollingBtn);
		jp.setBackground(Color.red); // TODO: debug
		jp.add(interruptPollingBtn);

		// text field settings
		dateField = new JTextField();
		dateField.setBounds(5, 5, 390, 30);
		dateField.setFont(new Font("Arial", Font.BOLD,16));
		dateField.setHorizontalAlignment(JTextField.RIGHT);
		dateField.setEditable(false);
		dateField.setBackground(Color.green); // TODO: debug
		dateField.setText("date");

//		output.setMargin(new Insets(0, 0, 0, 10));
		dateField.setBorder(null);

		jf.add(dateField);
		jf.add(jp);

		jf.setVisible(true);
		logger.info("App launched");

		dataPollingInterval = new Interval(() -> {
			JSONObject obj = HTTP.get("https://weather.fkor.us/api.php");
//			output.setText(String.format("%.2f", obj.getDouble("humidity")) + "%");
			dateField.setText(obj.getString("date"));
		}, 5000, "poll data every 5s");
		dataPollingInterval.start();
	}
}
