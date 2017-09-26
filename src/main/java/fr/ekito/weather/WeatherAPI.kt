package fr.ekito.weather

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by arnaud on 01/08/2016.
 */
@RestController
//class WeatherAPI(val apiKey: ApiKey, val googleMapsGeocodeWS: GoogleMapsGeocodeWS, val cacheManager: CacheManager) {
class WeatherAPI() {

    // logger
    var logger = LoggerFactory.getLogger(WeatherAPI::class.java)


    @RequestMapping(path = arrayOf("/hello"), method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    fun hello() = "hello :)"

//    @Scheduled(fixedRate = 3600000)
//    fun evictWeather() {
//        logger.warn("clear cache")
//        cacheManager.getCache("weathers").clear()
//    }

    //    @Cacheable("weathers")
    @RequestMapping(path = arrayOf("/weather"), method = arrayOf(RequestMethod.GET))
    fun weather(@RequestParam("lat") lat: String, @RequestParam("lon") lon: String, @RequestParam("lang") lang: String): String = WeatherApp.weather
//
//            : ResponseEntity<Weather> {
////        try {
////            val response = wundergroundWS.weather(apiKey.wunderground, lang.toUpperCase(), lat, lon).execute()
////            return ResponseEntity(response.body(), HttpStatus.valueOf(response.code()))
////        } catch (e: IOException) {
////            e.printStackTrace()
////            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
////        }
//        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
//    }

    //    @Cacheable("geocodes")
    @RequestMapping(path = arrayOf("/geocode"), method = arrayOf(RequestMethod.GET))
    fun geocode(@RequestParam("address") address: String): String = WeatherApp.geocode


//            : ResponseEntity<Geocode> {
////        return try {
////            val response = googleMapsGeocodeWS.weather(address, apiKey.geocode).execute()
////            ResponseEntity(response.body(), HttpStatus.valueOf(response.code()))
////        } catch (e: IOException) {
////            e.printStackTrace()
////            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
////        }
//        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
//}

}
