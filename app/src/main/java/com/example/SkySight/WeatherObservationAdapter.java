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

public class WeatherObservationAdapter extends RecyclerView.Adapter<WeatherObservationAdapter.WeatherObservationViewHolder> {
    private static final String TAG = "WeatherObsAdapter"; // Tag for logging
    private List<WeatherObservation> weatherObservationList;

    public WeatherObservationAdapter(List<WeatherObservation> weatherObservationList) {
        this.weatherObservationList = weatherObservationList;
    }

    @NonNull
    @Override
    public WeatherObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_observation, parent, false);
        return new WeatherObservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherObservationViewHolder holder, int position) {
        WeatherObservation observation = weatherObservationList.get(position);

        // Correctly formatting and displaying data in the views
        holder.tvTitle.setText(observation.getDay());
        holder.time.setText(observation.getTime());
        holder.condition.setText(observation.getCondition());
        holder.temperature.setText(String.format(observation.getTemperature() +"°C"));
        holder.winddirection.setText( observation.getWindDirection());
        holder.windspeed.setText(String.format(observation.getWindSpeed() +"m/s"));
        holder.humidity.setText(String.format("%s%%", observation.getHumidity() ));
        holder.pressure.setText(String.format( observation.getPressure()+"hPa"));

        double temp = Double.parseDouble(observation.getTemperature());
        if (temp <= 0) {
            holder.icon.setImageResource(R.drawable.night_clear);
        } else if (temp <= 12) {
            holder.icon.setImageResource(R.drawable.cloudy);
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
        return weatherObservationList != null ? weatherObservationList.size() : 0;
    }

    public static class WeatherObservationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,time,condition, temperature, winddirection, windspeed, humidity, pressure;
        ImageView icon;

        public WeatherObservationViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            time = itemView.findViewById(R.id.time);
            condition=itemView.findViewById(R.id.condition);
            temperature = itemView.findViewById(R.id.temp);
            winddirection = itemView.findViewById(R.id.winddirection);
            windspeed = itemView.findViewById(R.id.windspeed);
            humidity = itemView.findViewById(R.id.humidity);
            pressure = itemView.findViewById(R.id.pressure);

            icon = itemView.findViewById(R.id.iconView);
        }
    }
}
