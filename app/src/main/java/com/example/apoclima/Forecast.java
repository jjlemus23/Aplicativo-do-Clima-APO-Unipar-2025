package com.example.apoclima;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    @SerializedName("date")
    private String date;
    @SerializedName("weekday")
    private String weekday;
    @SerializedName("max")
    private int max;
    @SerializedName("min")
    private int min;
    @SerializedName("description")
    private String description;
    @SerializedName("condition")
    private String condition;

    // Getters e Setters
    public String getDate() {
        return date;
    }
    public String getWeekday() {
        return weekday;
    }
    public int getMax() {
        return max;
    }
    public int getMin() {
        return min;
    }
    public String getDescription() {
        return description;
    }
    public String getCondition() {
        return condition;
    }
}