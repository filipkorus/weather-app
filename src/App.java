package src;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import src.utils.HTTP;
import src.utils.Interval;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
	private final static Logger logger = Logger.getLogger(App.class);
	protected static JTextField output;
	protected static JButton stopPollingBtn, interruptPollingBtn;

	protected static Interval t1;
	public static void createAndShowGUI() {
		ActionListener as = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pressedBtnLabel = e.getActionCommand();
				logger.info("Button \"" + pressedBtnLabel + "\" pressed");

				Object pressedBtn = e.getSource();

				if (pressedBtn.equals(stopPollingBtn)) {
					Controller.handleStopPollingBtnClicked();
				} else if (pressedBtn.equals(interruptPollingBtn)) {
					t1.interrupt();
				}
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
		stopPollingBtn.addActionListener(as);
		interruptPollingBtn.addActionListener(as);

		jp.setBounds(5,50,390,300);
//		jp.setLayout(new GridLayout(5,4,5,5));
		jp.add(stopPollingBtn);
		jp.setBackground(Color.red); // TODO: debug
		jp.add(interruptPollingBtn);

		// ustawienia text field
		output = new JTextField();
		output.setBounds(5, 5, 390, 30);
		output.setFont(new Font("Arial", Font.BOLD,16));
		output.setHorizontalAlignment(JTextField.RIGHT);
		output.setEditable(false);
		output.setBackground(Color.green); // TODO: debug
		output.setText("date");

		//		output.setMargin(new Insets(0, 0, 0, 10));
		output.setBorder(null);

		jf.add(output);
		jf.add(jp);

		jf.setVisible(true);
		logger.info("App launched");

		t1 = new Interval(() -> {
				JSONObject obj = HTTP.get("https://weather.fkor.us/api.php");
//			output.setText(String.format("%.2f", obj.getDouble("humidity")) + "%");
				output.setText(obj.getString("date"));
		}, 5000, "poll data every 5s");
		t1.start();
	}
}
