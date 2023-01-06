package src.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;
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

	private final static Logger logger = Logger.getLogger(Database.class);

	Database() {
		Properties p = new Properties();
		File file = null;
		try {
			file = new File("db.properties");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

		try {
			logger.info("Reading db.properties");
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

	public ResultSet query(String query) {
		try {
			Connection conn = DriverManager.getConnection(this.DB_URL, this.USERNAME, this.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			logger.info("SQL: " + query);

			return rs;
		} catch (SQLException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.warn(sw.toString());
		}
		return null;
	}

	public static void main(String[] args) {
		Database db = new Database();

		new Thread(() -> {
			ResultSet rs = db.query("SELECT * FROM weather LIMIT 5");
			try {
				while(rs.next()) {
					System.out.println(rs.getDouble("hum"));
				}
			} catch (SQLException e) {}
		}).start();
	}
}
