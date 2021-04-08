package com.example.weathertoday.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainInfoEntity(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val minTemp: Double,
    @Json(name = "temp_max") val maxTemp: Double,
    @Json(name = "pressure") val pressure: Double,
    @Json(name = "humidity") val humidity: Double
)