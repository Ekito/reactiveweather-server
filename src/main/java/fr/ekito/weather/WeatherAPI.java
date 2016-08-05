package fr.ekito.weather;

import fr.ekito.weather.google.GoogleMapsGeocodeWS;
import fr.ekito.weather.google.json.geocode.Geocode;
import fr.ekito.weather.wunderground.WundergroundWS;
import fr.ekito.weather.wunderground.json.weather.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by arnaud on 01/08/2016.
 */
@RestController
public class WeatherAPI {

    // logger
    Logger logger = LoggerFactory.getLogger(WeatherAPI.class);

    @Autowired
    ApiKey apiKey;

    @Autowired
    WundergroundWS wundergroundWS;

    @Autowired
    GoogleMapsGeocodeWS googleMapsGeocodeWS;

    @Autowired
    CacheManager cacheManager;


    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "hello :)";
    }

    @Scheduled(fixedRate = 3600000)
    public void evictWeather() {
        logger.warn("clear cache");
        cacheManager.getCache("weathers").clear();
    }

    @Cacheable("weathers")
    @RequestMapping(path = "/weather", method = RequestMethod.GET)
    public ResponseEntity<Weather> weather(@RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam("lang") String lang) {
        try {
            Response<Weather> response = wundergroundWS.weather(apiKey.getWunderground(), lang.toUpperCase(), lat, lon).execute();
            return new ResponseEntity(response.body(), HttpStatus.valueOf(response.code()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable("geocodes")
    @RequestMapping(path = "/geocode", method = RequestMethod.GET)
    public ResponseEntity<Geocode> geocode(@RequestParam("address") String address) {
        try {
            Response<Geocode> response = googleMapsGeocodeWS.weather(address, apiKey.getGeocode()).execute();
            return new ResponseEntity(response.body(), HttpStatus.valueOf(response.code()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
