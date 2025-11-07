package com.example.apoclima;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProximosDiasFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView forecastRecyclerView;
    private ForecastAdapter forecastAdapter;

    private static final String BASE_URL = "https://api.hgbrasil.com/";
    private static final String API_KEY = "https://api.hgbrasil.com/weather?woeid=455827";

    public ProximosDiasFragment() {
        // Construtor público vazio é necessário
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proximos_dias, container, false);

        forecastRecyclerView = view.findViewById(R.id.forecast_recycler_view);
        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Puxa os dados da cidade e localização da HomeActivity
        if (getActivity() instanceof HomeActivity) {
            HomeActivity activity = (HomeActivity) getActivity();
            String city = activity.getCurrentCity();
            LatLng latLng = activity.getCurrentLatLng();
            
            fetchForecastData(city);
            updateMapLocation(latLng, city);
        } else {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Erro ao obter dados da Activity", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchForecastData(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<WeatherResponse> call = apiService.getCurrentWeather(API_KEY, city, "pt");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    List<Forecast> forecastList = response.body().getResults().getForecast();
                    updateForecastListUI(forecastList);
                } else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), "Nenhuma previsão futura encontrada.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), "Erro de conexão para previsão.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateForecastListUI(List<Forecast> forecastList) {
        forecastAdapter = new ForecastAdapter(forecastList);
        forecastRecyclerView.setAdapter(forecastAdapter);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Puxa a localização da activity quando o mapa estiver pronto
        if (getActivity() instanceof HomeActivity) {
            HomeActivity activity = (HomeActivity) getActivity();
            updateMapLocation(activity.getCurrentLatLng(), activity.getCurrentCity());
        }
    }

    public void updateMapLocation(LatLng latLng, String cityName) {
        if (mMap != null && latLng != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(cityName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }
}
