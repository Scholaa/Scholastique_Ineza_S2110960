//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SecondWeatherDataFetcher {

    public static String fetchXmlFromUrl(String urlString) throws Exception {
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
            throw new Exception("Failed to fetch XML data: HTTP error code: " + responseCode);
        }
        return response.toString();
    }

    public static void main(String[] args) {
        try {
            String xmlData = fetchXmlFromUrl("https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643123");
            WeatherObservationParser parser = new WeatherObservationParser();
            List<WeatherObservation> observations = parser.parseXml(xmlData);

            for (WeatherObservation observation : observations) {
                System.out.println(observation.getTitle());
                // Print other details as needed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

