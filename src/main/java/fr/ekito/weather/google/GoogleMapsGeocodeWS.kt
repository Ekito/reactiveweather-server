package fr.ekito.weather.google

import fr.ekito.weather.google.json.geocode.Geocode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by arnaud on 04/08/2016.
 */
interface GoogleMapsGeocodeWS {
    //https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY

    @GET("json")
    @Headers("Content-type: application/json")
    fun weather(@Query("address") address: String, @Query("wundergroundKey") apiKey: String): Call<Geocode>
}
