package com.ning.openweather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import org.jmock.*;
import org.jmock.api.Action;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.internal.ExpectationBuilder;
import org.jmock.internal.ExpectationCollector;

import com.ning.openweather.Weather.WeatherGetter;
import com.ning.openweather.interfaces.WeatherGetterInterface;
import com.ning.openweather.utils.Debugger;

//@RunWith(JMock.class)
public class WeatherTests {
	private JUnit4Mockery mockingContext;
	private Weather classUnderTest;
	private WeatherGetterInterface mockedDependency;
	
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
	
	@Test
	public void testGetCurrentTemperatureWithMock(){
		mockingContext = new JUnit4Mockery();
		mockedDependency = mockingContext.mock(WeatherGetterInterface.class);
		classUnderTest = new Weather("Oulu,fi", 3600);
		classUnderTest.setGetter(mockedDependency);
		mockingContext.checking(new Expectations() {
			{
				one(mockedDependency).doGetWeather();
				will(returnValue("{\"coord\":{\"lon\":25.47,\"lat\":65.01},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"base\":\"stations\",\"main\":{\"temp\":1.03,\"pressure\":1015,\"humidity\":86,\"temp_min\":0,\"temp_max\":2.78},\"visibility\":3600,\"wind\":{\"speed\":3.6,\"deg\":10},\"snow\":{\"1h\":0.25},\"clouds\":{\"all\":92},\"dt\":1554715637,\"sys\":{\"type\":1,\"id\":1351,\"message\":0.0042,\"country\":\"FI\",\"sunrise\":1554692993,\"sunset\":1554744593},\"id\":643493,\"name\":\"Oulu\",\"cod\":200}"));
			}
		});
		
		String temperature = classUnderTest.getCurrentTemperature();
		Debugger.log("tes case get curren temperature: "+temperature);
		float value = Float.valueOf(temperature);
		Debugger.log("test case temperature:"+value);
		assertEquals(temperature, "1.03");
		assertEquals(value, 1.03, 0.001);
	}
}
