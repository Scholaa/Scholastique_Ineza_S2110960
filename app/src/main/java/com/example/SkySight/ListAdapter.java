//Name: Scholastique Mukanoheri Ineza  ID: S2110960

package com.example.SkySight;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private List<ListModel> forecastItemList;
    private OnLocationItemClickListener listener;

    public ListAdapter(List<ListModel> forecastItemList, OnLocationItemClickListener listener) {
        this.forecastItemList = forecastItemList;
        this.listener = listener;
    }

    public interface OnLocationItemClickListener {
        void onLocationClick(ListModel item);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_weather_observation, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListModel item = forecastItemList.get(position);
        if (item != null) {
            holder.bindData(item);
        }
    }

    @Override
    public int getItemCount() {
        return forecastItemList.size();
    }

    public void updateData(List<ListModel> newData) {
        this.forecastItemList.clear();
        this.forecastItemList.addAll(newData);
        notifyDataSetChanged(); // Notifies the entire list has changed
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView location, temp, condition, maxTemp, minTemp;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            temp = itemView.findViewById(R.id.temp);
            condition = itemView.findViewById(R.id.condition);
            maxTemp = itemView.findViewById(R.id.maxTemp);
            minTemp = itemView.findViewById(R.id.minTemp);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onLocationClick(forecastItemList.get(position));
                }
            });
        }

        void bindData(ListModel item) {
            location.setText(item.getLocationId());
            condition.setText(item.getWeatherCondition());
            double maxTempValue = item.getMaxTemperature();
            double minTempValue = item.getMinTemperature();

            // Set a minimum temperature of 10°C if the temperature is 0
            if (maxTempValue == 0) {
                maxTempValue = 30; // Set the max temperature to 10°C
            }
            if (minTempValue == 0) {
                minTempValue = 10; // Set the min temperature to 10°C
            }

            temp.setText(String.format("%.0f°C", maxTempValue));
            maxTemp.setText(String.format("%.0f°C", maxTempValue));
            minTemp.setText(String.format("%.0f°C", minTempValue));
        }


    }
}




