package com.example.MINTOS.weather;

import jakarta.persistence.*;

@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @SequenceGenerator(
            name = "weather_sequence",
            sequenceName = "weather_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "weather_sequence"
    )
    private Long id;
    private String city;

    private String country;
    private Double longitude;
    private Double latitude;
    private String weather;
    private String temperature;

    public Weather() {
        this.city = "Unknown";
        this.country = "Unknown";
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.weather = "Unknown";
        this.temperature = "Unknown";
    }

    public Weather(String city, String country, Double longitude, Double latitude, String weather, String temperature) {
        this.city = city;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.weather = weather;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
