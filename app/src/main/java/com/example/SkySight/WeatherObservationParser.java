//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;



import static android.content.ContentValues.TAG;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherObservationParser {

    public List<WeatherObservation> parseXml(String xml) throws Exception {
        List<WeatherObservation> observations = new ArrayList<>();
        WeatherObservation currentObservation = null;
        boolean isInsideItem = false;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("item".equals(tagName)) {
                        currentObservation = new WeatherObservation();
                        isInsideItem = true;
                    } else if (isInsideItem) {
                        switch (tagName) {
                            case "title":
                                parseTitle(parser.nextText(), currentObservation);
                                break;
                            case "description":
                                parseDescription(parser.nextText(), currentObservation);
                                break;
                            case "pubDate":
                                currentObservation.setPubDate(parser.nextText());
                                break;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(tagName)) {
                        observations.add(currentObservation);
                        isInsideItem = false;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return observations;
    }

    private void parseTitle(String title, WeatherObservation observation) {
        // Example title format: "Monday - 12:00 BST: Light Cloud, 8Â°C (46Â°F)"
        Pattern pattern = Pattern.compile("^(.+?) - (.+?): (.+?),");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            observation.setDay(matcher.group(1));
            observation.setTime(matcher.group(2));
            observation.setCondition(matcher.group(3));
        }
    }

    private void parseDescription(String description, WeatherObservation observation) {
        findAndSetTemperature(description, observation);
        findAndSet(description, "Wind Direction: ([a-zA-Z ]+)", observation::setWindDirection);
        findAndSet(description, "Wind Speed: (\\d+)mph", observation::setWindSpeed);
        findAndSet(description, "Humidity: (\\d+)%", observation::setHumidity);
        findAndSet(description, "Pressure: (\\d+)mb", observation::setPressure);
    }

    private void findAndSet(String description, String regex, java.util.function.Consumer<String> setter) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            setter.accept(matcher.group(1));
        }
    }


    private void findAndSetTemperature(String description, WeatherObservation observation) {
        Pattern pattern = Pattern.compile("Temperature: (\\d+)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            String temperature = String.valueOf(Double.parseDouble(matcher.group(1)));
            observation.setTemperature(temperature);
            Log.d(TAG, "Parsed temperature: " + temperature);
        } else {
            Log.d(TAG, "Temperature pattern not found in description.");
        }
    }
}

