package com.example.apoclima;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("city_name") String cityName,
            @Query("locale") String locale
    );
}
