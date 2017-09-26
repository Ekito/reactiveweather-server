package fr.ekito.weather

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Created by arnaud on 01/08/2016.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
class WeatherApp : InitializingBean {

    @Autowired
    lateinit var ctx: ApplicationContext

    override fun afterPropertiesSet() {
        geocode = readJson(ctx.getResource("classpath:json/geocode_toulouse.json"))
        weather = readJson(ctx.getResource("classpath:json/weather_toulouse.json"))
    }

    fun readJson(fileRsr: Resource): String = fileRsr.inputStream.bufferedReader().useLines { sequence -> sequence.joinToString("\n") }

    // logger
//    val logger = LoggerFactory.getLogger(WeatherApp::class.java)

//    @Bean
//    fun wundergroundWSClient(): WundergroundWS {
//        val retrofit = Retrofit.Builder()
//                .baseUrl("http://api.wunderground.com/api/")
//                .client(createClient())
//                .addConverterFactory(GsonConverterFactory.create()).build()
//        return retrofit.create(WundergroundWS::class.java)
//    }

//    private fun createClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()

//    @Bean
//    fun googleMapsGeocodeWS(): GoogleMapsGeocodeWS {
//        val retrofit = Retrofit.Builder()
//                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
//                .client(createClient())
//                .addConverterFactory(GsonConverterFactory.create()).build()
//        return retrofit.create(GoogleMapsGeocodeWS::class.java)
//    }

//    @Bean
//    fun apiKey(@Value("\${WUNDERGROUND_KEY}") wundergroundKey: String, @Value("\${GEOCODE_KEY}") googleGeocodeApiKey: String): ApiKey {
//        logger.warn(" wunderground key : " + wundergroundKey)
//        logger.warn(" geocode key : " + googleGeocodeApiKey)
//        return ApiKey(wundergroundKey, googleGeocodeApiKey)
//    }

    companion object {
        lateinit var geocode: String
        lateinit var weather: String
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(WeatherApp::class.java)
}
