//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Inflate the portrait layout
            setContentView(R.layout.activity_notifications);
        } else {
            // Inflate the landscape layout
            setContentView(R.layout.landscape_notification);
        }

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupAdapter();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;

            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                intent = new Intent(this, MainActivity.class);
            } else if (id == R.id.navigation_maps) {
                intent = new Intent(this, MapViewActivity.class);
            } else if (id == R.id.navigation_weather) {
                intent = new Intent(this, ForecastActivity.class);
            } else if (id == R.id.navigation_notifications) {
                intent = new Intent(this,NotificationActivity.class);
            }

            // Check if an intent was assigned and start the activity
            if (intent != null) {
                startActivity(intent);
            }

            return true; // Return true to display the item as the selected item
        });

    }

    private void setupAdapter() {
        adapter = new NotificationsAdapter(this, NotificationManager.getNotifications(), this::deleteNotification);
        notificationsRecyclerView.setAdapter(adapter);
    }

    private void deleteNotification(String notificationId) {
        NotificationManager.deleteNotification(notificationId);
        adapter.notifyDataSetChanged(); // Refresh the RecyclerView
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotifications();
    }

    private void refreshNotifications() {
        // Assuming the weather data might have been updated elsewhere and notifications added
        adapter.updateNotifications(NotificationManager.getNotifications()); // You need to implement updateNotifications in your adapter
        adapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
    }
}
