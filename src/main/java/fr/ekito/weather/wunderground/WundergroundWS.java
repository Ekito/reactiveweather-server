package fr.ekito.weather.wunderground;

import fr.ekito.weather.wunderground.json.weather.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by arnaud on 02/08/2016.
 */
public interface WundergroundWS {

    @GET("{key}/forecast/lang:{lang}/pws:0/q/{lat},{lon}.json")
    @Headers("Content-type: application/json")
    Call<Weather> weather(@Path("key") String key, @Path("lang") String lang, @Path("lat") String lat, @Path("lon") String lon);
}
