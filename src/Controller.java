package src;

public class Controller  extends App {
	protected static void handleStopPollingBtnClicked() {
		if (t1.isRunning()) {
			t1.stop();
			stopPollingBtn.setText("Start polling data");
		} else {
			t1.start();
			stopPollingBtn.setText("Stop polling data");
		}
	}
}
