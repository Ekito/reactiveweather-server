package fr.ekito.weather;

import fr.ekito.weather.google.GoogleMapsGeocodeWS;
import fr.ekito.weather.wunderground.WundergroundWS;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arnaud on 01/08/2016.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class WeatherApp {

    // logger
    Logger logger = LoggerFactory.getLogger(WeatherApp.class);

    public static void main(String... args) {
        SpringApplication.run(WeatherApp.class);
    }

    @Bean
    WundergroundWS wundergroundWSClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.wunderground.com/api/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(WundergroundWS.class);
    }

    private OkHttpClient createClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
    }

    @Bean
    GoogleMapsGeocodeWS googleMapsGeocodeWS() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(GoogleMapsGeocodeWS.class);
    }

    @Bean
    ApiKey apiKey(@Value("${WUNDERGROUND_KEY}") String wundergroundKey, @Value("${GEOCODE_KEY}") String googleGeocodeApiKey) {
        logger.warn(" wunderground key : " + wundergroundKey);
        logger.warn(" geocode key : " + googleGeocodeApiKey);
        return new ApiKey(wundergroundKey, googleGeocodeApiKey);
    }
}
