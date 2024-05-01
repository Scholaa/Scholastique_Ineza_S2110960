//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherXmlParser {

    public List<ForecastItem> parseXml(String xml) throws Exception {
        List<ForecastItem> items = new ArrayList<>();
        ForecastItem currentItem = null;
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
                        currentItem = new ForecastItem();
                        isInsideItem = true;
                    } else if (isInsideItem) {
                        if ("title".equals(tagName)) {
                            String fullTitle = parser.nextText();
                            String[] titleParts = fullTitle.split(", ");
                            if (titleParts.length > 1) {
                                String dayAndCondition = titleParts[0]; // "Today: Light Rain"
                                currentItem.setTitle(dayAndCondition.split(":")[0].trim() + ":"); // "Today:"
                            } else {
                                currentItem.setTitle(fullTitle); // Fallback
                            }
                        } else if ("description".equals(tagName)) {
                            currentItem.setDescription(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(tagName) && currentItem != null) {
                        parseDescription(currentItem);
                        items.add(currentItem);
                        isInsideItem = false;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return items;
    }

    private void parseDescription(ForecastItem item) {
        String description = item.getDescription();
        Pattern pattern = Pattern.compile("Maximum Temperature: (\\d+).*Minimum Temperature: (\\d+).*Wind Direction: (\\w+).*Wind Speed: (\\d+).*Visibility: (\\w+).*Pressure: (\\d+).*Humidity: (\\d+).*UV Risk: (\\d+).*Pollution: (\\w+).*Sunrise: (\\S+).*Sunset: (\\S+)");
        Matcher matcher = pattern.matcher(description);

        if (matcher.find()) {
            item.setMaxTemperature(Double.parseDouble(matcher.group(1)));
            item.setMinTemperature(Double.parseDouble(matcher.group(2)));
            item.setWindDirection(matcher.group(3));
            item.setWindSpeed(Integer.parseInt(matcher.group(4)));
            item.setVisibility(matcher.group(5));
            item.setPressure(Integer.parseInt(matcher.group(6)));
            item.setHumidity(Integer.parseInt(matcher.group(7)));
            item.setUvRisk(Integer.parseInt(matcher.group(8)));
            item.setPollution(matcher.group(9));
            item.setSunrise(matcher.group(10));
            item.setSunset(matcher.group(11));
        }
    }


}

