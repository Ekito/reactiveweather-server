package fr.ekito.weather

import fr.ekito.weather.google.GoogleMapsGeocodeWS
import fr.ekito.weather.google.json.geocode.Geocode
import fr.ekito.weather.wunderground.WundergroundWS
import fr.ekito.weather.wunderground.json.weather.Weather
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import java.io.IOException

/**
 * Created by arnaud on 01/08/2016.
 */
@RestController
class WeatherAPI(val apiKey: ApiKey, val wundergroundWS: WundergroundWS, val googleMapsGeocodeWS: GoogleMapsGeocodeWS, val cacheManager: CacheManager) {

    // logger
    var logger = LoggerFactory.getLogger(WeatherAPI::class.java)


    @RequestMapping(path = arrayOf("/hello"), method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    fun hello() = "hello :)"

    @Scheduled(fixedRate = 3600000)
    fun evictWeather() {
        logger.warn("clear cache")
        cacheManager.getCache("weathers").clear()
    }

    @Cacheable("weathers")
    @RequestMapping(path = arrayOf("/weather"), method = arrayOf(RequestMethod.GET))
    fun weather(@RequestParam("lat") lat: String, @RequestParam("lon") lon: String, @RequestParam("lang") lang: String): ResponseEntity<Weather> {
        try {
            val response = wundergroundWS.weather(apiKey.wunderground, lang.toUpperCase(), lat, lon).execute()
            return ResponseEntity(response.body(), HttpStatus.valueOf(response.code()))
        } catch (e: IOException) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @Cacheable("geocodes")
    @RequestMapping(path = arrayOf("/geocode"), method = arrayOf(RequestMethod.GET))
    fun geocode(@RequestParam("address") address: String): ResponseEntity<Geocode> {
        try {
            val response = googleMapsGeocodeWS!!.weather(address, apiKey!!.geocode).execute()
            return ResponseEntity(response.body(), HttpStatus.valueOf(response.code()))
        } catch (e: IOException) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
