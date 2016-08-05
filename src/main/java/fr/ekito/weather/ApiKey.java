package fr.ekito.weather;

/**
 * Created by arnaud on 04/08/2016.
 */
public class ApiKey {
    String wunderground;
    String geocode;

    public ApiKey(String wunderground, String geocode) {
        this.wunderground = wunderground;
        this.geocode = geocode;
    }

    public String getWunderground() {
        return wunderground;
    }

    public String getGeocode() {
        return geocode;
    }
}
