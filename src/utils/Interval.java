package utils;

import org.apache.log4j.Logger;

/**
 * Interval class provides easy-to-use API for running Runnable in interval (every given time).
 */
public class Interval {
	// logger for Interval class
	private final Logger logger = Logger.getLogger(Interval.class);

	// Runnable instance to run every given interval time
	private final Runnable runnable;

	// time in milliseconds to run Runnable instance every given interval time
	private final int interval;

	// name of the interval
	private final String name;

	// working thread
	private Thread thread;

	/**
	 *
	 * @param runnable Runnable instance to run in working thread in the Interval
	 * @param interval Time (in milliseconds) to run runnable every `interval` milliseconds
	 * @param name Name of Interval
	 */
	public Interval(Runnable runnable, int interval, String name) {
		this.runnable = runnable;
		this.interval = interval;
		this.name = name;
	}

	/**
	 *
	 * @param runnable Runnable instance to run in working thread in the Interval
	 * @param interval Time (in milliseconds) to run runnable every `interval` milliseconds
	 */
	public Interval(Runnable runnable, int interval) {
		this.runnable = runnable;
		this.interval = interval;
		this.name = String.valueOf(this.hashCode());
	}

	/**
	 * starts working thread in the Interval
	 */
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
		this.logger.debug("Interval \"" + this.name + "\" started");
	}

	/**
	 * temporary stops working thread in the Interval
	 */
	public void stop() {
		this.thread.stop();
		this.logger.debug("Interval \"" + this.name + "\" stopped");
	}

	/**
	 * interrupts working thread in the Interval
	 */
	public void interrupt() {
		if (this.thread.isAlive()) {
			this.thread.interrupt();
		}
	}

	/**
	 * method to check is interval currently running
	 * @return is interval currently running
	 */
	public boolean isRunning() {
		return this.thread.isAlive();
	}

	/**
	 * getter for name of interval
	 * @return name of interval
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getter for interval time
	 * @return interval time (in millisecond)
	 */
	public int getInterval() {
		return this.interval;
	}
}
