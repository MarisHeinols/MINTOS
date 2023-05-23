package com.example.MINTOS.weather.service;
import com.example.MINTOS.weather.repository.WeatherRepository;
import com.example.MINTOS.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

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
        // Check if returns data for unknown ip
        Weather testWeather =  weatherService.getWeather("0.0.0.0");
        assertEquals(testWeather.getIp(), "0.0.0.0");
        assertEquals(testWeather.getCity(), "Unknown");
    }
    @Test
    void canGetActualIpWeather() {
        Weather testWeather =  weatherService.getWeather(googleIp);
        // Check if values that don`t change day by day are updated
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

        // Test default weather code
        String result4 = weatherService.checkWeatherCode(0);
        assertEquals("Clear", result4);

    }
}