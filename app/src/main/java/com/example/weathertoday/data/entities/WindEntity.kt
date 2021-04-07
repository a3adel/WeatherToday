package com.example.weathertoday.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindEntity(
    @Json(name = "speed") val speed: Double,
    @Json(name = "degree") val degree: Double
)