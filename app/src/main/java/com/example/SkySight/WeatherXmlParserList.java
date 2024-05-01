//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class WeatherXmlParserList {

    private static final String TAG = "ListParser";

    public List<ListModel> parseXml(String xml) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));

        List<ListModel> forecastItemList = new ArrayList<>();
        ListModel forecastItem = null;
        boolean insideFirstItem = false;
        boolean firstArrayProcessed = false;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT && !firstArrayProcessed) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("item".equals(tagName) && !insideFirstItem) {
                        forecastItem = new ListModel();
                        insideFirstItem = true;
                    } else if (insideFirstItem && "title".equals(tagName)) {
                        String title = parser.nextText();
                        forecastItem.setTitle(extractTitle(title));
                        forecastItem.setWeatherCondition(extractCondition(title));
                    } else if (insideFirstItem && "description".equals(tagName)) {
                        String description = parser.nextText();
                        parseDescription(forecastItem, description);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(tagName) && insideFirstItem) {
                        forecastItemList.add(forecastItem);
                        insideFirstItem = false;
                        firstArrayProcessed = true; // Set the flag to true after processing the first array
                    }
                    break;
            }
            eventType = parser.next();
        }

        return forecastItemList; // Return the list of ForecastItem objects
    }


    private String extractTitle(String title) {
        return "Today";
    }


    private String extractCondition(String title) {
        Pattern pattern = Pattern.compile("Today: ([^,]+),");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Clear";
    }

    private void parseDescription(ListModel item, String description) {
        Log.d(TAG, "Parsing description: " + description);

        // Split the description into parts
        String[] parts = description.split(", ");
        for (String part : parts) {
            try {
                if (part.contains("Maximum Temperature:")) {
                    // Extract only the Celsius temperature part, ignoring Fahrenheit
                    String temp = part.substring(part.indexOf("Maximum Temperature:") + "Maximum Temperature:".length(), part.indexOf("°C")).trim();
                    item.setMaxTemperature(Double.parseDouble(temp));
                    Log.d(TAG, "Parsing maxtemp: " + item.getMaxTemperature());
                } else if (part.contains("Minimum Temperature:")) {
                    // Extract only the Celsius temperature part, ignoring Fahrenheit
                    String temp = part.substring(part.indexOf("Minimum Temperature:") + "Minimum Temperature:".length(), part.indexOf("°C")).trim();
                    item.setMinTemperature(Double.parseDouble(temp));
                    Log.d(TAG, "Parsing mintemp: " + item.getMinTemperature());
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Failed to parse number from part: " + part, e);
            } catch (StringIndexOutOfBoundsException e) {
                Log.e(TAG, "Malformed part (missing '°C' or value): " + part, e);
            }
        }
    }




}
