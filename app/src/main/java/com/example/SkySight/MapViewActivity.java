//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SkySight.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {


    String[] cities = {"Mauritius", "Bangladesh", "Oman", "London", "New York", "Glasgow"};
    private Map<String, ListModel> weatherData = new HashMap<>();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize map and other components
        initializeMap();
        setupButton();
        // Pre-fetch weather data for all locations
        fetchWeatherDataForAllLocations();

    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void fetchWeatherDataForAllLocations() {
        // URL and other initialization details
        Map<String, String> locationUrls = getLocationUrlMappings();

        for (Map.Entry<String, String> entry : locationUrls.entrySet()) {
            String cityKey = entry.getKey();
            String url = entry.getValue();
            fetchWeatherDataForLocation(cityKey, url);
        }
    }

    private void fetchWeatherDataForLocation(final String cityKey, String url) {
        new AsyncTask<String, Void, List<ListModel>>() {
            @Override
            protected List<ListModel> doInBackground(String... urls) {
                try {
                    String xmlData = WeatherDataFetcher.fetchXmlFromUrl(urls[0]);
                    WeatherXmlParserList parser = new WeatherXmlParserList();
                    return parser.parseXml(xmlData);  // Return the parsed list directly
                } catch (Exception e) {
                    Log.e("WeatherData", "Error fetching or parsing data for " + cityKey, e);
                    return null;  // Return null in case of failure
                }
            }

            @Override
            protected void onPostExecute(List<ListModel> result) {
                if (result != null && !result.isEmpty()) {
                    // Assuming there's a method or a place to store these results
                    updateWeatherObservations(cityKey, result);
                } else {
                    Log.e("WeatherData", "No data fetched or empty list for " + cityKey);
                }
            }
        }.execute(url);
    }

    private void updateWeatherObservations(String cityKey, List<ListModel> models) {
       ListModel observation = new ListModel();
        observation.setLocationId(cityKey);
        weatherData.put(cityKey, observation);  // Assuming weatherData is a Map<String, WeatherObservation>
    }



    private Map<String, String> getLocationUrlMappings() {
        Map<String, String> locationUrls = new HashMap<>();
        locationUrls = new HashMap<>();
        locationUrls.put("mauritius", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154");
        locationUrls.put("glasgow", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
        locationUrls.put("london", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743");
        locationUrls.put("newyork", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
        locationUrls.put("bangladesh", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241");
        locationUrls.put("oman", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286");
        return locationUrls;
    }


    private void showCitySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a City");
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectCity(cities[which]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Setting initial zoom level to see the whole world
        LatLng initialPosition = new LatLng(0, 0);  // Center of the world
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 1));
    }

    private void selectCity(String cityName) {

      ListModel observation = weatherData.get(cityName.toLowerCase());
        if (observation != null) {


            LatLng city = null;
            String markerTitle = "";

            switch (cityName) {
                case "Mauritius":
                    city = new LatLng(-20.348404, 57.552152);
                    Marker marker = mMap.addMarker(new MarkerOptions().position(city).title(cityName));
                    marker.setSnippet("The weather is hot with a bit of clouds\n temperature is a" +
                            "bit hot");
                    break;
                case "Bangladesh":
                    city = new LatLng(23.6850, 90.3563);
                    Marker marker1 = mMap.addMarker(new MarkerOptions().position(city).title(cityName));
                    marker1.setSnippet("The weather is cloud with a bit of rains\n temperature is a" +
                            "bit mild");

                    break;
                case "Oman":
                    city = new LatLng(21.4735, 55.9754);
                    Marker marker2 = mMap.addMarker(new MarkerOptions().position(city).title(cityName));
                    marker2.setSnippet("The weather is clear with a bit of clouds\n temperature is a" +
                            "bit mild");


                    break;
                case "London":
                    city = new LatLng(51.5074, -0.1278);
                    Marker marker3 = mMap.addMarker(new MarkerOptions().position(city).title(cityName));
                    marker3.setSnippet("The weather is clear with a bit of wind\n temperature is a" +
                            "warm");


                    break;
                case "New York":
                    city = new LatLng(40.7128, -74.0060);
                    Marker marker4 = mMap.addMarker(new MarkerOptions().position(city).title(cityName));
                    marker4.setSnippet("The weather  cloudsy\n Expecting rains");


                    break;
                case "Glasgow":
                    city = new LatLng(55.8642, -4.2518);
                    Marker marker5 = mMap.addMarker(new MarkerOptions().position(city).title(cityName));
                    marker5.setSnippet("The weather is clear with a bit of clouds\n temperature is a" +
                            "bit mild");

                    break;
            }

            if (city != null) {
                mMap.addMarker(new MarkerOptions().position(city).title(markerTitle));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(city, 10));  // Zoom to city level
            }
        }
    }

    private void setupButton() {
        Button selectCityButton = new Button(this);
        selectCityButton.setText("Select City");
        selectCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCitySelectionDialog();
            }
        });

        FrameLayout buttonContainer = findViewById(R.id.buttonContainer);
        if (buttonContainer != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            selectCityButton.setLayoutParams(layoutParams);
            buttonContainer.addView(selectCityButton);
        } else {
            Log.e("MapViewActivity", "Button container not found.");
        }
    }

}





