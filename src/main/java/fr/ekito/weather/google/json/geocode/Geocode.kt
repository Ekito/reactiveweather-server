package fr.ekito.weather.google.json.geocode

import java.util.*

data class Geocode(var status: String? = null, var results: List<Result> = ArrayList())
