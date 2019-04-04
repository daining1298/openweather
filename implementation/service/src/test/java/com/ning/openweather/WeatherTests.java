package com.ning.openweather;

import org.junit.Test;

public class WeatherTests {
	private String getTemperature(String location) {
		Weather weather = new Weather(location);
		return weather.getCurrentTemperature();
	}
	
	@Test
	public void testGetCurrentTemperature() {
		String temperature = getTemperature("Oulu,fi");
		float f = Float.valueOf(temperature);
		
		assert(f < 40);
		assert(f > -40);
	}
}
