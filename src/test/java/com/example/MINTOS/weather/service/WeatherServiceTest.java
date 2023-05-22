package com.example.MINTOS.weather.service;
import com.example.MINTOS.weather.repository.WeatherRepository;
import com.example.MINTOS.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {WeatherService.class})
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock
    private WeatherRepository weatherRepository;
    private WeatherService weatherService;
    @Mock
    private Weather weather;
    private String googleIp = "8.8.8.8";


    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(weatherRepository);
        weather = new Weather(
                "Mountain View",
                googleIp,
                "United States",
                0.0,
                0.0,
                "Unknown",
                "Unknown"
        );
    }

    @Test
    void canGetWeather() {
        Weather testWeather =  weatherService.getWeather("0.0.0.0");
        assertEquals(testWeather.getIp(), "0.0.0.0");
        assertEquals(testWeather.getCity(), "Unknown");
    }
    @Test
    void canGetActualIpWeather() {
        Weather testWeather =  weatherService.getWeather(googleIp);
        assertEquals(testWeather.getIp(), weather.getIp());
        assertEquals(testWeather.getCity(), weather.getCity());
        assertEquals(testWeather.getCountry(), weather.getCountry());
    }

    @Test
    void canGetAllWeather() {
        weatherService.getAllWeather();
        verify(weatherRepository).findAll();
    }
    @Test
    void canUpdateValues() {
        Weather testWeather = weatherService.getWeather("0.0.0.0");
        assertEquals(testWeather.getIp(), "0.0.0.0");
    }
    @Test
    void valuesNotUpdated() {
        Weather testWeather = weatherService.getWeather("0.0.0.0");
    }

    @Test
    public void testCheckWeatherCode() {

        // Test weather code 2
        String result1 = weatherService.checkWeatherCode(2);
        assertEquals("Cloudy", result1);

        // Test weather code 55
        String result2 = weatherService.checkWeatherCode(55);
        assertEquals("Drizzle", result2);

        // Test weather code 95
        String result3 = weatherService.checkWeatherCode(95);
        assertEquals("Thunderstorm", result3);

        String result4 = weatherService.checkWeatherCode(0);
        assertEquals("Clear", result4);

    }
}