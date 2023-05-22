package com.example.MINTOS;

import com.example.MINTOS.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MintosApplicationTests {

	@Autowired
	private WeatherRepository weatherRepository;

}
