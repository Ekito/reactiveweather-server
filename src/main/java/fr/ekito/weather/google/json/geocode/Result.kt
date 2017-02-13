package fr.ekito.weather.google.json.geocode

import java.util.*

data class Result(var address_components: List<AddressComponent> = ArrayList(), var formatted_address: String? = null, var geometry: Geometry? = null, var place_id: String? = null, var types: List<String> = ArrayList())
