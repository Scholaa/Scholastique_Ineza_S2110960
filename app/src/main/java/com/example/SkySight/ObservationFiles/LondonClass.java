package com.example.SkySight.ObservationFiles;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SkySight.ForecastFiles.London;
import com.example.SkySight.MainActivity;
import com.example.SkySight.MapViewActivity;
import com.example.SkySight.NotificationActivity;
import com.example.SkySight.NotificationManager;
import com.example.SkySight.R;
import com.example.SkySight.SecondWeatherDataFetcher;
import com.example.SkySight.WeatherObservation;
import com.example.SkySight.WeatherObservationAdapter;
import com.example.SkySight.WeatherObservationParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class LondonClass extends AppCompatActivity {

    private RecyclerView weatherRecyclerView;
    private WeatherObservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Inflate the portrait layout
            setContentView(R.layout.observer_activity_main);
        } else {
            // Inflate the landscape layout
            setContentView(R.layout.landscape_obsever_activity_main);
        }
        Log.d("WeatherApp", "WeatherObservationActivity started");

        weatherRecyclerView = findViewById(R.id.weatherObserverRecyclerView);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;

            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                intent = new Intent(this, MainActivity.class);
            } else if (id == R.id.navigation_maps) {
                intent = new Intent(this, MapViewActivity.class);
            } else if (id == R.id.navigation_weather) {
                intent = new Intent(this, London.class);
            } else if (id == R.id.navigation_notifications) {
                intent = new Intent(this, NotificationActivity.class);
            }

            // Check if an intent was assigned and start the activity
            if (intent != null) {
                startActivity(intent);
            }

            return true; // Return true to display the item as the selected item
        });

        // Execute the AsyncTask to fetch weather observations
        new FetchWeatherObservationsTask().execute("https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743");
    }

    private class FetchWeatherObservationsTask extends AsyncTask<String, Void, List<WeatherObservation>> {
        @Override
        protected List<WeatherObservation> doInBackground(String... urls) {
            try {
                String xmlData = SecondWeatherDataFetcher.fetchXmlFromUrl(urls[0]);
                WeatherObservationParser parser = new WeatherObservationParser();
                return parser.parseXml(xmlData);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<WeatherObservation> observations) {
            if (observations != null) {
                Log.d("WeatherApp", "Observations list size: " + observations.size());

                // Here, for each observation, check if a notification needs to be added
                for (WeatherObservation observation : observations) {
                    NotificationManager.checkAndAddNotificationForWeatherObservation(observation);
                }

                adapter = new WeatherObservationAdapter(observations);
                weatherRecyclerView.setAdapter(adapter);
            } else {
                Log.e("WeatherApp", "Failed to fetch weather observations.");
            }
        }



    }
}
