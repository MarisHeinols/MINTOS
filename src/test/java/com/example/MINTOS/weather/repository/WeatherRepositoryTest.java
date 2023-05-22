package com.example.MINTOS.weather.repository;

import com.example.MINTOS.weather.model.Weather;
import com.example.MINTOS.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    void findWeatherByIp() {
        String ip = "192.0.0.0";
        Weather weather = new Weather(
                "Test",
                ip,
                "Test",
                0.0,
                0.0,
                "Test snow",
                "13");
        List<Weather> myList = new ArrayList<>();
        myList.add(weather);
        weatherRepository.save(weather);

        List<Weather> weatherInDb = weatherRepository.findWeatherByIp(ip);
        assertEquals(weatherInDb,myList);

    }
}