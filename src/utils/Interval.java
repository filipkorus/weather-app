package src.utils;

import org.apache.log4j.Logger;

public class Interval {
	private final Logger logger = Logger.getLogger(Interval.class);
	private final Runnable runnable;
	private final int interval;
	private final String name;
	private Thread thread;

	public Interval(Runnable runnable, int interval, String name) {
		this.runnable = runnable;
		this.interval = interval;
		this.name = name;
	}
	public void start() {
		this.thread = new Thread(() -> {
			while (true) {
				this.runnable.run();

				try {
					Thread.sleep(this.interval);
				} catch (InterruptedException e) {
					this.logger.warn("Interval \"" + this.name + "\" interrupted");
				}
			}
		});

		this.thread.start();
		this.logger.info("Interval \"" + this.name + "\" started");
	}

	public void stop() {
		this.thread.stop();
		this.logger.info("Interval \"" + this.name + "\" stopped");
	}

	public void interrupt() {
		if (this.thread.isAlive()) {
			this.thread.interrupt();
		}
	}

	public boolean isRunning() {
		return this.thread.isAlive();
	}

	public String getName() {
		return this.name;
	}

	public int getInterval() {
		return this.interval;
	}
}
