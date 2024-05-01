//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataFetcher {

    public static String fetchXmlFromUrl(String urlString) throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            } finally {
                connection.disconnect();
            }
        } else {
            throw new IOException("Failed to fetch XML data: HTTP error code: " + responseCode);
        }
        return response.toString();
    }
}

