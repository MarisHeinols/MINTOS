package com.example.MINTOS.weather;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final RequestService requestService;
    @Autowired
    public WeatherController(WeatherService weatherService, RequestService requestService) {
        this.weatherService = weatherService;
        this.requestService = requestService;
    }

    @GetMapping
    public Weather getWeather(HttpServletRequest request) {
        String clientIpAddress = requestService.getClientsIpAddress(request);
        return weatherService.getWeather(clientIpAddress);
    }

}
