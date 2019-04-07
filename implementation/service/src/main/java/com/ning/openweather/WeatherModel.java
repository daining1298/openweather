package com.ning.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherModel {
	public Object coord;
	public Object weather;
	public String base;
	
	@JsonProperty("main")
	private Main main = new Main();
	
	public int visibility;
	public Object wind;
	public Object clouds;
	public int dt;
	public Object sys;
	public int id;
	public String name;
	public int cod;
	
	static class Main {
		@JsonProperty("temp")
		private double temp;
		
		@JsonProperty("")
		public double pressure;
		
		@JsonProperty("")
		public double humidity;
		
		@JsonProperty("")
		public double temp_min;
		
		@JsonProperty("")
		public double temp_max;
		
		public double getTemp() {return temp;}
		public void setTemp(double temp) {this.temp = temp; }
		
	}
	
	public Main getMain () {return main;}
	public void setMain(Main main) {this.main = main;}
}
