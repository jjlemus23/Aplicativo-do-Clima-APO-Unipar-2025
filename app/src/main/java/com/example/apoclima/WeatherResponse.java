package com.example.apoclima;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("results")
    private WeatherResults results;

    public WeatherResults getResults() {
        return results;
    }

    public void setResults(WeatherResults results) {
        this.results = results;
    }
}
