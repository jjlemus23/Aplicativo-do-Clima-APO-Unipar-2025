package com.example.apoclima;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResults {

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("temp")
    private int temp;

    @SerializedName("description")
    private String description;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("wind_speedy")
    private String windSpeedy;

    @SerializedName("condition_slug")
    private String conditionSlug;

    @SerializedName("forecast")
    private List<Forecast> forecast;

    public String getCityName() {
        return cityName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getWindSpeedy() {
        return windSpeedy;
    }

    public String getConditionSlug() {
        return conditionSlug;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }
}
