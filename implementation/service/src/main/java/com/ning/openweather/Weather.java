package com.ning.openweather;

import com.ning.openweather.interfaces.*;
import com.ning.openweather.utils.Debugger;

import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

class Weather implements WeatherGetterInterface {
	static final String OPEN_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?"
			+ "q=%s&units=metric&&APPID=37aadf112bad11882bc795c536634e32";
	private String mLocation;
	
	public Weather(String location) {
		mLocation = location;
	}
	
	public String getCurrentTemperature(){
		String result = "{\"error\"=\"failed to get weather\"}";
		URL url;
		try {
			url = new URL(String.format(OPEN_WEATHER_URL, mLocation));
	        JsonFactory factory = new JsonFactory();
	        JsonParser parser;
	        parser = factory.createParser(url);
		 
	        while (!parser.isClosed()) {
	            JsonToken token = parser.nextToken();
	            if (token == null)
	                break;
		 
	            if (JsonToken.FIELD_NAME.equals(token) && "main".equals(parser.getCurrentName())) {
	            	while (true) {
	            		token = parser.nextToken();
	                    if (token == null)
	                        break;
	                    if (JsonToken.FIELD_NAME.equals(token) && "temp".equals(parser.getCurrentName())) {
	                        token = parser.nextToken();
	                        Debugger.log("temperature found "+parser.getText());
	                        return String.valueOf(parser.getText());
	                    }
		 
	                }
	            }
	        }
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getCurrentWeather(String location, int intervalSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHourlyForcast(String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get16DaysForcase(String location) {
		// TODO Auto-generated method stub
		return null;
	}
}