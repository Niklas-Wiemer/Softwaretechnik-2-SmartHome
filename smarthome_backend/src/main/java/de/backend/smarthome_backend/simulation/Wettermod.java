package de.backend.smarthome_backend.simulation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wettermod {

    @JsonProperty("id")
    private double id;

    @JsonProperty("temp")
    private double temp;

    @JsonProperty("tempMin")
    private double tempMin;

    @Override
    public String toString() {
        return "WeatherModel{" +
                "id=" + id +
                ", temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", feelsLike=" + feelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dewPoint=" + dewPoint +
                ", cloud=" + cloud +
                ", weatherId=" + weatherId +
                ", weatherName='" + weatherName + '\'' +
                ", weatherDesc='" + weatherDesc + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windDeg=" + windDeg +
                ", windGust=" + windGust +
                ", rain1h=" + rain1h +
                ", snow1h=" + snow1h +
                '}';
    }

    @JsonProperty("tempMax")
    private double tempMax;

    @JsonProperty("feelsLike")
    private double feelsLike;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("dewPoint")
    private double dewPoint;

    @JsonProperty("cloud")
    private int cloud;

    @JsonProperty("weatherId")
    private int weatherId;

    @JsonProperty("weatherName")
    private String weatherName;

    @JsonProperty("weatherDesc")
    private String weatherDesc;

    @JsonProperty("weatherIcon")
    private String weatherIcon;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("windSpeed")
    private double windSpeed;

    @JsonProperty("windDeg")
    private int windDeg;

    @JsonProperty("windGust")
    private double windGust;

    @JsonProperty("rain1h")
    private Double rain1h;

    @JsonProperty("snow1h")
    private Double snow1h;

    public double getTemp() {
        return feelsLike;
    }

    public int getCloud() {
        return cloud;
    }

    public String getWeatherName() {
        return weatherName;
    }
}