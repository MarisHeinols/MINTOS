package com.example.MINTOS.weather.controler;

import com.example.MINTOS.weather.model.Weather;
import com.example.MINTOS.weather.service.RequestService;
import com.example.MINTOS.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@ExtendWith(SpringExtension.class)
class WeatherControllerTest {

    private static String url = "/weather";
    private static String ip = "192.0.0.0";
    private Weather weather;
    private List<Weather> weatherList;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherService weatherService;
    @MockBean
    private RequestService requestService;

    @BeforeEach
    void setUp() {
        weather = new Weather(
                "Test",
                ip,
                "Test",
                0.0,
                0.0,
                "Test snow",
                "13");
        weatherList = new ArrayList<>();
        weatherList.add(weather);
    }
    @Test
    void getWeather() throws Exception {
        when(weatherService.getWeather(ip)).thenReturn(weather);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .content(asJsonString(weather))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getAllWeather() throws Exception {
        when(weatherService.getAllWeather()).thenReturn(weatherList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .content(asJsonString(weatherList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    public static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
