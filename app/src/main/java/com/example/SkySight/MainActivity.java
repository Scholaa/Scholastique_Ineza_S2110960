//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.widget.EditText;
        import android.widget.Toast;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;


        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;


        import android.content.Context;

        import com.example.SkySight.ObservationFiles.BangladeshClass;
        import com.example.SkySight.ObservationFiles.GlasGowClass;
        import com.example.SkySight.ObservationFiles.LondonClass;
        import com.example.SkySight.ObservationFiles.MauritiusClass;
        import com.example.SkySight.ObservationFiles.NewYorkClass;
        import com.example.SkySight.ObservationFiles.OmanClass;


public class MainActivity extends Activity implements ListAdapter.OnLocationItemClickListener {

    private RecyclerView weatherObservationsRecyclerView;
    private ListAdapter listAdapter;
    private Map<String, String> locationUrls; // Map to store location IDs and their corresponding URLs

    private List<ListModel> originalForecastItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Determine the current orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Inflate the portrait layout
            setContentView(R.layout.activity_weather_observations);
        } else {
            // Inflate the landscape layout
            setContentView(R.layout.landscape_activity_weather_observation);


        }



    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText searchField = findViewById(R.id.search_field);
        weatherObservationsRecyclerView = findViewById(R.id.rvWeatherObservations);
        weatherObservationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        originalForecastItemList = new ArrayList<>();
        listAdapter = new ListAdapter(new ArrayList<>(), this);
        weatherObservationsRecyclerView.setAdapter(listAdapter);

        // Initialize the map with location IDs and their corresponding URLs
        locationUrls = new HashMap<>();
       locationUrls.put("Mauritius", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154");
        locationUrls.put("Glasgow", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
        locationUrls.put("London", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743");
       locationUrls.put("Newyork", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
       locationUrls.put("Bangladesh", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241");
       locationUrls.put("Oman", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286");



        // Fetch weather data for all locations
        fetchWeatherDataForAllLocations();




        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Filter data based on text input
                filterData(s.toString());
            }
        });

    }




    // Fetch weather data for all locations
    private void fetchWeatherDataForAllLocations() {
        for (String locationId : locationUrls.keySet()) {
            String apiUrl = locationUrls.get(locationId);
            fetchWeatherDataForLocation(apiUrl, locationId); // Pass locationId here
        }
    }





    private void fetchWeatherDataForLocation(String apiUrl, final String locationId) {
        Log.d("ListActivity", "Fetching weather data for location: " + locationId);
        // AsyncTask implementation to fetch and parse the weather data
        new AsyncTask<String, Void, List<ListModel>>() {
            @Override
            protected List<ListModel> doInBackground(String... urls) {
                try {
                    Log.d("ListActivity", "Fetching XML from URL for : "  + locationId + apiUrl);
                    String xmlData = WeatherDataFetcher.fetchXmlFromUrl(urls[0]);
                    Log.d("ListActivity", "XML data fetched successfully."  + locationId);
                    WeatherXmlParserList parser = new WeatherXmlParserList();
                    List<ListModel> result = parser.parseXml(xmlData);
                    // Set locationId for each ListModel item here
                    for (ListModel item : result) {
                        item.setLocationId(locationId);
                    }
                    Log.d("ListActivity", "Weather data parsed and locationId set successfully for:" + locationId);
                    return result;
                } catch (Exception e) {
                    Log.e("ListActivity", "Error fetching or parsing weather data for:" + locationId, e);
                    return null;
                }
            }

            protected void onPostExecute(List<ListModel> result) {
                if (result != null && !result.isEmpty()) {
                    // Add new results to the original list
                    originalForecastItemList.addAll(result);
                    // Update the adapter with the entire list of fetched data
                    listAdapter.updateData(new ArrayList<>(originalForecastItemList)); // Make a copy if necessary
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch weather data for location: " + locationId, Toast.LENGTH_SHORT).show();
                }
            }



        }.execute(apiUrl);
    }

    // Method to filter data based on the search query
    private void filterData(String query) {
        // Check if the query is empty and reset the adapter data to the original list
        if (query.isEmpty()) {
            listAdapter.updateData(originalForecastItemList);
        } else {
            // Otherwise, filter the original list based on the query
            List<ListModel> filteredList = new ArrayList<>();
            for (ListModel item : originalForecastItemList) {
                if (item.getLocationId().trim().equalsIgnoreCase(query.trim())) {
                    filteredList.add(item);
                }
            }
            listAdapter.updateData(filteredList); // Update the adapter with the filtered list
        }
    }


    @Override
    public void onLocationClick(ListModel item) {
        Intent intent = null;
        Context context = this; // Or getActivity() if this is in a Fragment
        String locationName = item.getLocationId();

        switch (locationName) {
            case "Mauritius":
                intent = new Intent(this, MauritiusClass.class);
                break;
            case "Glasgow":
                intent = new Intent(this, GlasGowClass.class);
                break;
            case "London":
                intent = new Intent(this, LondonClass.class);
                break;
            case "Newyork":
                intent = new Intent(this, NewYorkClass.class);
                break;
            case "Bangladesh":
                intent = new Intent(this, BangladeshClass.class);
                break;
            case "Oman":
                intent = new Intent(this, OmanClass.class);
                break;

        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Activity not found for location: " + locationName, Toast.LENGTH_SHORT).show();
        }
    }
}


