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
	private final static Logger logger = Logger.getLogger(HTTP.class);
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
