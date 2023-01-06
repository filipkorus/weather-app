package src;

public class Controller  extends App {
	protected static void handleStopPollingBtnClicked() {
		if (dataPollingInterval.isRunning()) {
			dataPollingInterval.stop();
			stopPollingBtn.setText("Start polling data");
		} else {
			dataPollingInterval.start();
			stopPollingBtn.setText("Stop polling data");
		}
	}
}
