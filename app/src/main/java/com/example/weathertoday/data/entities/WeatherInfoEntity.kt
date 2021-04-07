package com.example.weathertoday.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoEntity(
    @Json(name = "weather") val weather: WeatherEntity,
    @Json(name = "main") val main: MainInfoEntity,
    @Json(name = "wind")val windEntity: WindEntity,
    @Json(name = "clouds")val cloudEntity:CloudEntity,
    @Json(name = "rain")val rainEntity:RainEntity?=null,
    @Json(name = "snow")val snowEntity:SnowEntity?=null,
)