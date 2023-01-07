package src.utils;

import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Database {
	enum DB {
		DB_URL,
		USERNAME,
		PASSWORD
	}

	private final String DB_URL;
	private final String USERNAME;
	private final String PASSWORD;

	private final Logger logger = Logger.getLogger(Database.class);

	public Database() {
		Properties p = new Properties();
		File file = null;
		try {
			file = new File("db.properties");
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
	}

	private ResultSet query(String query) {
		try {
			Connection conn = DriverManager.getConnection(this.DB_URL, this.USERNAME, this.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			this.logger.debug("SQL query: " + query);

			return rs;
		} catch (SQLException | com.mysql.cj.exceptions.CJCommunicationsException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			this.logger.warn(sw.toString());
		}
		return null;
	}

	public DefaultCategoryDataset createDataset(LocalDate day) {
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
			return null;
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String series1 = "Outdoor temperature";

		int entriesCount = 0;
		try {
			while(rs.next()) {
				String hr = rs.getString("hr"), min = rs.getString("min");

				dataset.addValue(rs.getDouble("temp_out"), series1, hr + ":" + (min.length() == 1 ? "0" + min : min));

				++entriesCount;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}

		return entriesCount == 0 ? null : dataset;
	}
}
