package com.example.SkySight.ForecastFiles;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SkySight.ForecastAdapter;
import com.example.SkySight.ForecastItem;
import com.example.SkySight.MainActivity;
import com.example.SkySight.MapViewActivity;
import com.example.SkySight.NotificationActivity;
import com.example.SkySight.R;
import com.example.SkySight.WeatherDataFetcher;
import com.example.SkySight.WeatherObservationActivity;
import com.example.SkySight.WeatherXmlParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Oman extends AppCompatActivity {

    private RecyclerView weatherRecyclerView;
    private ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Inflate the portrait layout
            setContentView(R.layout.activity_oman);
        } else {
            // Inflate the landscape layout
            setContentView(R.layout.landscape_oman);
        }

        // Initialize RecyclerView
        initRecyclerView();


        // Fetch weather forecast data
        fetchWeatherForecastData();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null; // Declare intent reference outside the conditions

            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                intent = new Intent(this, MainActivity.class);
            } else if (id == R.id.navigation_maps) {
                intent = new Intent(this, MapViewActivity.class);
            } else if (id == R.id.navigation_weather) {
                intent = new Intent(this, WeatherObservationActivity.class);
            } else if (id == R.id.navigation_notifications) {
                intent = new Intent(this, NotificationActivity.class);

            }

            // Check if an intent was assigned and start the activity
            if (intent != null) {
                startActivity(intent);
            }

            return true; // Return true to display the item as the selected item
        });



    }

    private void initRecyclerView() {
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }







    private void fetchWeatherForecastData() {
        // Asynchronously fetch weather forecast data
        new FetchWeatherDataTask().execute("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
    }

    private class FetchWeatherDataTask extends AsyncTask<String, Void, List<ForecastItem>> {
        @Override
        protected List<ForecastItem> doInBackground(String... urls) {
            try {
                String xmlData = WeatherDataFetcher.fetchXmlFromUrl(urls[0]);
                WeatherXmlParser parser = new WeatherXmlParser();
                return parser.parseXml(xmlData);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ForecastItem> forecastItems) {
            super.onPostExecute(forecastItems);
            if (forecastItems != null) {
                // Update RecyclerView with fetched data
                adapter = new ForecastAdapter(forecastItems);
                weatherRecyclerView.setAdapter(adapter);
            } else {
                // Handle fetch data failure (e.g., show a toast message)
                Toast.makeText(Oman.this, "Failed to fetch weather data.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
