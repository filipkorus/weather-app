package src.utils;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {
	private final static Logger logger = Logger.getLogger(HTTP.class);
	public static JSONObject get(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// set request params
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();

			logger.info("HTTP GET " + urlString + " " + connection.getResponseCode());

			// read body
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line, body = "";
			while ((line = reader.readLine()) != null) { body += line; }

			connection.disconnect();

			return new JSONObject(body);
		} catch (IOException e) {
			logger.error(e.getCause());
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}

		return null;
	}
}
