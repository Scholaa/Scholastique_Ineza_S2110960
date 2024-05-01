//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

public class ListModel {

    private String title;
    private String weatherCondition;
    private String description;
    // Parsed data
    private String forecastSummary;
    private double minTemperature;
    private double maxTemperature;
    private String windDirection;
    private int windSpeed;
    private String visibility;
    private int pressure;
    private int humidity;
    private int uvRisk;
    private String pollution;
    private String sunrise;
    private String sunset;
    private String locationId;


    private boolean isVisible = true;
    public ListModel() {
    }
    
    

    public ListModel(String title, String weatherCondition, String description, String forecastSummary, double minTemperature, double maxTemperature, String windDirection, int windSpeed, String visibility, int pressure, int humidity, int uvRisk, String pollution, String sunrise, String sunset, String locationId) {
        this.title = title;
        this.weatherCondition = weatherCondition;
        this.description = description;
        this.forecastSummary = forecastSummary;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvRisk = uvRisk;
        this.pollution = pollution;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.locationId = locationId;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getForecastSummary() {
        return forecastSummary;
    }

    public void setForecastSummary(String forecastSummary) {
        this.forecastSummary = forecastSummary;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(int uvRisk) {
        this.uvRisk = uvRisk;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "ListModel{" +
                "title='" + title + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", description='" + description + '\'' +
                ", forecastSummary='" + forecastSummary + '\'' +
                ", minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", windDirection='" + windDirection + '\'' +
                ", windSpeed=" + windSpeed +
                ", visibility='" + visibility + '\'' +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", uvRisk=" + uvRisk +
                ", pollution='" + pollution + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", locationId='" + locationId + '\'' +
                ", isVisible=" + isVisible +
                '}';
    }
}
