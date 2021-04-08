package com.example.weathertoday.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherEntity(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String?=null,
    @Json(name = "description") val description: String?=null,
    @Json(name = "icon") val iconId: String?=null
)
