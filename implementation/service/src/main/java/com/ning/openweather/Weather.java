package com.ning.openweather;

import com.ning.openweather.interfaces.*;
import com.ning.openweather.utils.Debugger;

import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

class Weather {
	class WeatherGetter implements WeatherGetterInterface{
		public String doGetWeather() {
			String result = "{\"error\"=\"failed to get weather\"}";
	        try {
                HttpURLConnection con;
                con = (HttpURLConnection) new URL(String.format(OPEN_WEATHER_URL, mLocation)).openConnection();
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
	        return result;
		}
	}
	
	static final String ERROR_MESSAGE = "error: failed to get current temperature";
	static final String OPEN_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?"
			+ "q=%s&units=metric&&APPID=37aadf112bad11882bc795c536634e32";
	private String mLocation;
	private int mInterval = 0; // requesting weather periodically in mInverval Seconds
	private WeatherGetterInterface mGetter = new WeatherGetter();
	
	public Weather(String location) {
		mLocation = location;
	}
	
	public Weather(String location, int interval) {
		mLocation = location;
		mInterval = interval;
	}
	
	public void setGetter(WeatherGetterInterface mockedDependency){
		mGetter = mockedDependency;
	}
	
	public String getCurrentTemperature(){
		String result = ERROR_MESSAGE;
		URL url;
		if(mInterval > 0) {
			//just to try and demo Object mapping
			//TODO: periodic weather getting and pass back to client
			String jsonString = mGetter.doGetWeather();
			return parseCurrentTemperature(jsonString);
		}
		else {
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
		}
		return result;
	}
	
	private String parseCurrentTemperature(String jsonString) {
		String temperature = ERROR_MESSAGE;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		    WeatherModel w = objectMapper.readValue(jsonString, WeatherModel.class);

		    Debugger.log("mapping result = " + w.getMain().getTemp());
		    
		    return String.valueOf(w.getMain().getTemp());
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return temperature;
	}
}