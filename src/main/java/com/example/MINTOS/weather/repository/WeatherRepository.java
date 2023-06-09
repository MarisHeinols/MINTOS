package com.example.MINTOS.weather.repository;

import com.example.MINTOS.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findWeatherByIp(String ip);
}
