//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationManager {
    private static List<AppNotification> notifications = new ArrayList<>();

    public static void addNotification(AppNotification notification) {
        notifications.add(notification);
    }

    public static List<AppNotification> getNotifications() {
        return notifications;
    }

    public static void deleteNotification(String notificationId) {
        notifications.removeIf(notification -> notification.getId().equals(notificationId));
    }

    // Clears all notifications
    public static void clearNotifications() {
        notifications.clear();
    }

    // Checks weather observation and adds a notification
    public static void checkAndAddNotificationForWeatherObservation(WeatherObservation observation) {

        try {
            float temperature = Float.parseFloat(observation.getTemperature().replaceAll("[^\\d.]", "")); // Remove non-numeric characters
            if (temperature > 30.0) {
                String message = "High temperature alert: " + temperature + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "High Temperature", message));
            }else if (temperature > 20.0) {
                String message = "Minimal temperature alert: " + temperature + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "Minimal Temperature", message));
            } else if (temperature > 10.0) {
                String message = "Warm temperature alert: " + temperature + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "Warm Temperature", message));
            }else if (temperature < 10.0) {
                String message = "Cold temperature alert: " + temperature + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "Cold Temperature", message));
            }
        } catch (NumberFormatException e) {
            Log.e("NotificationManager", "Error parsing temperature", e);
        }

        try {
            float humidity = Float.parseFloat(observation.getHumidity().replaceAll("[^\\d.]", ""));
            if (humidity > 30.0) {
                String message = "High Humidity alert: " + humidity + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "High Humidity", message));
            }else if (humidity > 20.0) {
                String message = " Minimal Humidity alert: " + humidity + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "Minimal Humidity", message));
            } else if (humidity > 10.0) {
                String message = " Humidity alert: " + humidity + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "Adjustable Humidity", message));
            }else if (humidity < 10.0) {
                String message = " Humidity alert: " + humidity + "°C";
                addNotification(new AppNotification(UUID.randomUUID().toString(), "Low Humidity", message));
            }
        } catch (NumberFormatException e) {
            Log.e("NotificationManager", "Error parsing temperature", e);
        }
    }
}
