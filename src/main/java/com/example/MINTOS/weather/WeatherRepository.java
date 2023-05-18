package com.example.MINTOS.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findWeatherByCity(String city);

}
