package fr.ekito.weather

import fr.ekito.weather.google.GoogleMapsGeocodeWS
import fr.ekito.weather.wunderground.WundergroundWS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by arnaud on 01/08/2016.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
class WeatherApp {

    // logger
    val logger = LoggerFactory.getLogger(WeatherApp::class.java)

    @Bean
    fun wundergroundWSClient(): WundergroundWS {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.wunderground.com/api/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(WundergroundWS::class.java)
    }

    private fun createClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()

    @Bean
    fun googleMapsGeocodeWS(): GoogleMapsGeocodeWS {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(GoogleMapsGeocodeWS::class.java)
    }

    @Bean
    fun apiKey(@Value("\${WUNDERGROUND_KEY}") wundergroundKey: String, @Value("\${GEOCODE_KEY}") googleGeocodeApiKey: String): ApiKey {
        logger.warn(" wunderground key : " + wundergroundKey)
        logger.warn(" geocode key : " + googleGeocodeApiKey)
        return ApiKey(wundergroundKey, googleGeocodeApiKey)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(WeatherApp::class.java)
}
