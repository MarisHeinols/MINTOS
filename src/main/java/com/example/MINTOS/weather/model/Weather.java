package com.example.MINTOS.weather.model;

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
    private String ip;
    private String city;
    private String country;
    private Double longitude;
    private Double latitude;
    private String weather;
    private String temperature;
    private String time;

    public Weather() {
        this.city = "Unknown";
        this.ip = "Unknown";
        this.country = "Unknown";
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.weather = "Unknown";
        this.temperature = "Unknown";
        this.time = "Unknown";
    }

    public Weather(String city, String ip, String country, Double longitude, Double latitude, String weather, String temperature) {
        this.city = city;
        this.ip = ip;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
