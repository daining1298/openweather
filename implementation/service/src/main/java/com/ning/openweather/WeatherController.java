package com.ning.openweather;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ning.openweather.utils.Debugger;

@RestController
public class WeatherController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * get current weather
     * @param location
     * @param units
     * @param interval in seconds, 0 means get weather only once.
     * @return
     */
    @RequestMapping("/weather")
    public Weather weather(@RequestParam(value="location", defaultValue="Oulu,fi") String location,
    		@RequestParam(value="units", defaultValue="metric") String units,
    		@RequestParam(value="interval", defaultValue="0") int interval) {
    	if(interval == 0) {
    		Debugger.log("get weather only once for location " + location);

            return new Weather("Oulu,fi");
    	}
    	else {
    		Debugger.log("get weather for location " + location + " with interval " + interval);

            return new Weather("Oulu,fi", interval);
    	}
    }
}
