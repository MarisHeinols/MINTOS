package com.example.MINTOS.weather.service;

import com.example.MINTOS.weather.model.Weather;
import com.example.MINTOS.weather.repository.WeatherRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
        Weather weather = weatherReadingExists(ip);
        if(weather.getIp() == "Unknown"){
            weather.setIp(ip);
            addLocationOfIp(weather, ip);
            addWeatherData(weather);
            saveWeatherToDb(weather);
        }
        return weather;
    }

    public List<Weather> getAllWeather(){
        return weatherRepository.findAll();
    }

    @CacheEvict("weather_by_id")
    private void saveWeatherToDb(Weather weather){
        weatherRepository.save(weather);
    }
    @Cacheable("weather_by_id")
    private List<Weather> getAllWeatherByIp(String ip){
        return weatherRepository.findWeatherByIp(ip);
    }

    private Weather weatherReadingExists(String ip){
        Weather weather = new Weather();
        List<Weather> weatherByIp = getAllWeatherByIp(ip);

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        String [] timeStampParts = timeStamp.split("_");
        String timeNow = timeStampParts[1];
        String monthNow = timeStampParts[0];
        String [] timeParts = timeNow.split(":");

        for (Weather weatherFromIp:weatherByIp) {
            if(weatherFromIp.getTime().equals(timeNow) || weatherFromIp.getTime().contains(monthNow) && timeParts[0].equals(weatherFromIp.getTime().subSequence(11,13))){
                return  weatherFromIp;
            }
        }
        weather.setTime(timeStamp);
        return weather;

    }


    private void addLocationOfIp(Weather weather, String ip){
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

    public String checkWeatherCode(Integer weatherCode){
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
