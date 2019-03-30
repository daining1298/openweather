package com.ning.openweather;

import com.ning.openweather.interfaces.*;
import com.ning.openweather.utils.Debugger;

import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

class Weather implements WeatherGetterInterface {
	static final String OPEN_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?"
			+ "q=%s&unit=metric&&APPID=37aadf112bad11882bc795c536634e32";
	private String mLocation;
	
	public Weather(String location) {
		mLocation = location;
	}
	
	public String getCurrentWeather() {
		String result = "{\"error\"=\"failed to get weather\"}";
		URL url;
		try {
			url = new URL(String.format(OPEN_WEATHER_URL, mLocation));
			HttpURLConnection con;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				int status = con.getResponseCode();
				
				Debugger.log("Response code:"+status);
				
				BufferedReader in = new BufferedReader(
						  new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				}
				in.close();
				
				Debugger.log(content);
				
				return content.toString();
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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