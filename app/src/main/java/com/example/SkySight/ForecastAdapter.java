//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastItem> forecastItemList;

    public ForecastAdapter(List<ForecastItem> forecastItemList) {
        this.forecastItemList = forecastItemList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastItem item = forecastItemList.get(position);
        //holder.weatherConditionTextView.setText(item.getWeatherCondition());
        holder.titleTextView.setText(item.getTitle());
        holder.minTemperatureTextView.setText(String.valueOf(item.getMinTemperature()) + "°C");
        holder.maxTemperatureTextView.setText(String.valueOf(item.getMaxTemperature()) + "°C");
        holder.humidity.setText(String.valueOf(item.getHumidity()) + "%");
        holder.pressure.setText(String.valueOf(item.getPressure()) + " hPa");
        holder.windDirection.setText(item.getWindDirection());
        holder.windSpeed.setText(String.valueOf(item.getWindSpeed()) + " mph");
        holder.sunRise.setText(item.getSunrise());
        holder.sunSet.setText(item.getSunset());

        double temp = Double.parseDouble(String.valueOf(item.getMaxTemperature()));
        if (temp <= 0) {
            holder.icon.setImageResource(R.drawable.night_clear);
        } else if (temp <= 12) {
            holder.icon.setImageResource(R.drawable.night_partial_cloud);
        } else if (temp <= 24) {
            holder.icon.setImageResource(R.drawable.day_partial_cloud);
        } else if (temp <= 35) {
            holder.icon.setImageResource(R.drawable.day_clear);
        } else {
            holder.icon.setImageResource(R.drawable.day_clear);
        }
    }


    @Override
    public int getItemCount() {
        return forecastItemList.size();
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView,weatherConditionTextView;

        public TextView maxTemperatureTextView, minTemperatureTextView,windDirection,windSpeed,pressure,humidity,sunRise,sunSet;

        ImageView icon;
        public ForecastViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            weatherConditionTextView = itemView.findViewById(R.id.weatherConditionTextView);
            minTemperatureTextView = itemView.findViewById(R.id.minTemperatureTextView);
            maxTemperatureTextView = itemView.findViewById(R.id.maxTemperatureTextView);
            windSpeed =itemView.findViewById(R.id.windSpeedTextView);
            windDirection = itemView.findViewById(R.id.winddirectionTextView);
            pressure = itemView.findViewById(R.id.pressureTextView);
            humidity = itemView.findViewById(R.id.humidityTextView);
            sunRise = itemView.findViewById(R.id.sunriseTextView);
            sunSet = itemView.findViewById(R.id.sunsetTextView);
            icon = itemView.findViewById(R.id.icon);


        }
    }
}

