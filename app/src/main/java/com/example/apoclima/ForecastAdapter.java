package com.example.apoclima;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<com.example.apoclima.Forecast> forecastList;

    public ForecastAdapter(List<com.example.apoclima.Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        com.example.apoclima.Forecast forecast = forecastList.get(position);
        holder.dayDateTextView.setText(forecast.getWeekday() + ", " + forecast.getDate());
        holder.tempRangeTextView.setText(forecast.getMin() + "°C / " + forecast.getMax() + "°C");
        holder.forecastDescriptionTextView.setText(forecast.getDescription());

        // Lógica para definir o ícone do clima
        // Use 'condition' ou 'description' da API, dependendo do que o slug representa melhor
        switch (forecast.getCondition()) { // Assumindo que 'condition' é o slug curto
            case "clear_day":
            case "sunny":
                holder.forecastIconImageView.setImageResource(R.drawable.ic_weather_sunny);
                break;
            case "cloudly_day":
            case "cloud":
                holder.forecastIconImageView.setImageResource(R.drawable.ic_weather_cloudy);
                break;
            case "rain":
            case "storm":
                holder.forecastIconImageView.setImageResource(R.drawable.ic_weather_rain);
                break;
            // Adicione mais casos conforme os slugs de previsão da API HG Brasil
            default:
                holder.forecastIconImageView.setImageResource(R.drawable.ic_weather_placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dayDateTextView;
        TextView tempRangeTextView;
        TextView forecastDescriptionTextView;
        ImageView forecastIconImageView;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayDateTextView = itemView.findViewById(R.id.day_date_text_view);
            tempRangeTextView = itemView.findViewById(R.id.temp_range_text_view);
            forecastDescriptionTextView = itemView.findViewById(R.id.forecast_description_text_view);
            forecastIconImageView = itemView.findViewById(R.id.forecast_icon_image_view);
        }
    }
}