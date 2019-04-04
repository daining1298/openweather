package com.ning.openweather.interfaces;

public interface WeatherGetterInterface {
	
	public String getCurrentTemperature();
	
	public String getCurrentWeather(String location, int intervalSeconds);
	
	public String getHourlyForcast(String location);
	
	public String get16DaysForcase(String location);

}
