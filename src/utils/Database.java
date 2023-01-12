package utils;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

/**
 * Database API
 */
public class Database {
	/**
	 * enum with fields needed to be read from resources/db.properties file
	 */
	enum DB {
		DB_URL,
		USERNAME,
		PASSWORD
	}

	/**
	 * database credentials
 	 */
	private final String DB_URL, USERNAME, PASSWORD;

	/**
	 * database connection instance
	 */
	private Connection connection = null;

	/**
	 * logger for Database class
	 */
	private final Logger logger = Logger.getLogger(Database.class);

	/**
	 * reads resources/db.properties (file containing database credentials), establish connection to database
	 */
	public Database() {
		Properties p = new Properties();
		File file = null;
		try {
			file = new File("resources/db.properties");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		try {
			logger.debug("Reading db.properties");
			FileReader reader = new FileReader(file);
			p.load(reader);
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		this.DB_URL = p.getProperty(DB.DB_URL.toString());
		this.USERNAME = p.getProperty(DB.USERNAME.toString());
		this.PASSWORD = p.getProperty(DB.PASSWORD.toString());

		if (this.DB_URL == null || this.USERNAME == null || this.PASSWORD == null) {
			this.logger.error("Database credentials not set up properly in db.properties file");
			System.exit(1);
		}

		establishConnection();
	}

	/**
	 * establish connection to database
	 */
	private void establishConnection() {
		try {
			this.connection = DriverManager.getConnection(this.DB_URL, this.USERNAME, this.PASSWORD);
		} catch (SQLException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			this.logger.warn(sw.toString());
//			System.exit(1);
		}
	}

	/**
	 * executes SQL query
	 * @param query SQL query string
	 * @return ResultSet with results from database
	 */
	private ResultSet query(String query) {
		while (this.connection == null) {
			establishConnection();
		}

		ResultSet rs = null;
		try {
			Statement stmt = this.connection.createStatement();
			this.logger.debug("SQL query: " + query);
			rs = stmt.executeQuery(query);

		} catch (SQLException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			this.logger.warn(sw.toString());
		}
		return rs;
	}

	/**
	 * creates dataset from given day
	 * @param day day to create dataset from
	 * @return DatabaseResults with response from database
	 */
	public DatabaseResults createDataset(LocalDate day) {
		String month = "" + day.getMonthValue();
		if (month.length() == 1) {
			month = "0" + month;
		}

		String dayNumber = "" + day.getDayOfMonth();
		if (dayNumber.length() == 1) {
			dayNumber = "0" + dayNumber;
		}

		String date = day.getYear() + "-" + month + "-" + dayNumber;

		ResultSet rs = this.query("SELECT temp_out, HOUR(datetime) as hr, MINUTE(datetime) as min FROM weather WHERE DATE(datetime) = '" + date + "' ORDER BY datetime ASC;");
		if (rs == null) {
			return new DatabaseResults("Database error", 500, null);
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int entriesCount = 0;

		try {
			while(rs.next()) {
				String hr = rs.getString("hr"), min = rs.getString("min");

				dataset.addValue(rs.getDouble("temp_out"), "Outdoor temperature", hr + ":" + (min.length() == 1 ? "0" + min : min));

				++entriesCount;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}

		return entriesCount == 0 ?
			new DatabaseResults("no dataset", 404, null) :
			new DatabaseResults("success", 200, dataset);
	}
}
