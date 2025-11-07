package com.example.apoclima;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HojeFragment extends Fragment {

    private static final String TAG = "HojeFragment";
    private static final String BASE_URL = "https://api.hgbrasil.com/";
    private static final String API_KEY = "https://api.hgbrasil.com/weather?woeid=455827";

    private TextView cityNameTextView, dateTextView, temperatureTextView,
            descriptionTextView, humidityTextView, windTextView, errorMessageTextView;
    private ImageView weatherIconImageView;
    private ProgressBar progressBar;
    private CardView weatherCardView;
    private ApiService apiService;

    public HojeFragment() {
        // Construtor público vazio é necessário
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoje, container, false);

        cityNameTextView = view.findViewById(R.id.city_name_text_view);
        dateTextView = view.findViewById(R.id.date_text_view);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
        descriptionTextView = view.findViewById(R.id.description_text_view);
        humidityTextView = view.findViewById(R.id.humidity_text_view);
        windTextView = view.findViewById(R.id.wind_text_view);
        weatherIconImageView = view.findViewById(R.id.weather_icon_image_view);
        progressBar = view.findViewById(R.id.progress_bar);
        weatherCardView = view.findViewById(R.id.weather_card_view);
        errorMessageTextView = view.findViewById(R.id.error_message);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa o Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Puxa o nome da cidade da HomeActivity
        if (getActivity() instanceof HomeActivity) {
            HomeActivity activity = (HomeActivity) getActivity();
            String city = activity.getCurrentCity();
            fetchWeatherData(city);
        } else {
            showError("Erro: Não foi possível obter a cidade da activity.");
        }
    }

    private void fetchWeatherData(String city) {
        Log.d(TAG, "Buscando dados do tempo para a cidade: " + city);
        progressBar.setVisibility(View.VISIBLE);
        weatherCardView.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.GONE);

        Call<WeatherResponse> call = apiService.getCurrentWeather(API_KEY, city, "pt");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    updateUI(response.body().getResults());
                    weatherCardView.setVisibility(View.VISIBLE);
                } else {
                    showError("Dados de clima não encontrados para esta cidade.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Falha na chamada à API.", t);
                showError("Erro de conexão: Verifique sua internet.");
            }
        });
    }

    private void updateUI(WeatherResults results) {
        cityNameTextView.setText(results.getCityName());
        dateTextView.setText(results.getDate() + " " + results.getTime());
        temperatureTextView.setText(results.getTemp() + "°C");
        descriptionTextView.setText(results.getDescription());
        humidityTextView.setText("Umidade: " + results.getHumidity() + "%");
        windTextView.setText("Vento: " + results.getWindSpeedy());

        // Mapeamento de ícones
        switch (results.getConditionSlug()) {
            case "clear_day":
            case "sunny":
                weatherIconImageView.setImageResource(R.drawable.ic_weather_sunny);
                break;
            case "cloudly_day":
            case "cloud":
                weatherIconImageView.setImageResource(R.drawable.ic_weather_cloudy);
                break;
            case "rain":
            case "storm":
                weatherIconImageView.setImageResource(R.drawable.ic_weather_rain);
                break;
            default:
                weatherIconImageView.setImageResource(R.drawable.ic_weather_placeholder);
        }
    }

    private void showError(String message) {
        errorMessageTextView.setText(message);
        errorMessageTextView.setVisibility(View.VISIBLE);
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
