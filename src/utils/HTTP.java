package utils;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {
	// logger for HTTP class
	private final static Logger logger = Logger.getLogger(HTTP.class);

	/**
	 *
	 * @param urlString URL to make HTTP GET request
	 * @return HTTP GET response as a JSON object or null if there was an error
	 */
	public static JSONObject get(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// set request params
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();

			logger.debug("HTTP GET " + urlString + " " + connection.getResponseCode());

			// read body
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder body = new StringBuilder();
			while ((line = reader.readLine()) != null) body.append(line);

			connection.disconnect();

			return new JSONObject(body.toString());
		} catch (IOException | JSONException e) {
			logger.warn(e.getMessage());
		}

		return null;
	}
}
