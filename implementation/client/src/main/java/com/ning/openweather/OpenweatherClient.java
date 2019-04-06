package com.ning.openweather;

import java.io.Console;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class OpenweatherClient {
	final static String OPEN_WEATHER_URL = "http://localhost:8080/weather?location=%s";

	public static void main(String[] args) throws IOException {
		System.out.println(">>> Welcom to Ning's weather service <<<");
		System.out.println("Please type the city you want to search and hit Enter, e.g. Oulu,fi");
		System.out.println("Or just hit Enter, by default it will fetch Oulu's temperature");
		
		String location="Oulu,fi";
		Console console = System.console();
		String input  = console.readLine("Please enter location : ");  
		if(!input.isEmpty()) {
			location = input;
		}
		URL url = new URL(String.format(OPEN_WEATHER_URL, location));
        JsonFactory factory = new JsonFactory();
        JsonParser parser;
        parser = factory.createParser(url);
        
        while (!parser.isClosed()) {
            JsonToken token = parser.nextToken();
            if (token == null)
                break;
	 
            if (JsonToken.FIELD_NAME.equals(token) && "currentTemperature".equals(parser.getCurrentName())) {
            	token = parser.nextToken();
            	System.out.println(location + "'s current temperature is "+parser.getText() + " degree celsius.");
            }
        }
	}
}
