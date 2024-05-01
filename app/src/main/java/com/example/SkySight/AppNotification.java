//Name: Scholastique Mukanoheri Ineza  ID: S2110960

        package com.example.SkySight;

public class AppNotification {
    private String id;
    private String title;
    private String message;
    private long timestamp;


    public AppNotification(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }


    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

