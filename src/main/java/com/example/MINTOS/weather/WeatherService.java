package com.example.MINTOS.weather;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final WebClient.Builder builder = WebClient.builder();
    private final JSONParser parser = new JSONParser();

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather getWeather(String ip) {
        Weather weather = new Weather();
        addLocationOfIp(weather, ip);
        addWeatherData(weather);
        weatherRepository.save(weather);
        return weather;


    }

    private void addLocationOfIp(Weather weather, String ip){
        System.out.println("IPPPPPPPPPPPPPPPPP IS");
        System.out.println(ip);
        String location = builder.build().get().uri("https://ipapi.co/"+ip+"/json").retrieve()
                .bodyToMono(String.class).block();
        try {
            JSONObject locationAsJson = (JSONObject) parser.parse(location);
            if(locationAsJson.containsKey("city")){
                weather.setCity(locationAsJson.get("city").toString());
                weather.setCountry(locationAsJson.get("country_name").toString());
                weather.setLatitude(
                        Double.valueOf((locationAsJson.get("latitude").toString())));
                weather.setLongitude( Double.valueOf((locationAsJson.get("longitude").toString())));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addWeatherData(Weather weather){
        if(!weather.getCity().equals("Unknown")){
            String weatherData = builder
                    .build()
                    .get()
                    .uri("https://api.open-meteo.com/v1/forecast?latitude="+weather.getLatitude()+"&longitude="+weather.getLongitude()+"&current_weather=true")
                    .retrieve()
                    .bodyToMono(String.class).block();
            try {
                JSONObject weatherDataAsJson = (JSONObject) parser.parse(weatherData);
                JSONObject currentWeather = (JSONObject) parser.parse(weatherDataAsJson.get("current_weather").toString());
                weather.setTemperature(currentWeather.get("temperature").toString());

                Integer weatherCode = Integer.valueOf((currentWeather.get("weathercode").toString()));
                weather.setWeather(checkWeatherCode(weatherCode));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String checkWeatherCode(Integer weatherCode){
        if(weatherCode>0 && weatherCode<4){
            return "Cloudy";
        }else if(weatherCode>44 && weatherCode<49){
            return "Fogy";
        }else if(weatherCode>50 && weatherCode<58){
            return "Drizzle";
        }else if(weatherCode>60 && weatherCode<68){
            return "Rainy";
        }else if(weatherCode>70 && weatherCode<78){
            return "Snowy";
        }else if(weatherCode>80 && weatherCode<83){
            return "Rain shower";
        }else if(weatherCode>84 && weatherCode<87){
            return "Snow shower";
        }else if(weatherCode>90){
            return "Thunderstorm";
        }else{
            return "Clear";
        }
    }
}
