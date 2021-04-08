package com.example.weathertoday.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SnowEntity(
    @Json(name = "1h") val snowVolumeLastHour: Double? = null,
    @Json(name = "3h") val snowVolumeLast3Hours: Double? = null,
)
