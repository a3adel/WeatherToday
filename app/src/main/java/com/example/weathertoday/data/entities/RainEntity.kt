package com.example.weathertoday.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RainEntity(
    @Json(name = "1h") val rainVolumeLastHour: Double? = null,
    @Json(name = "3h") val rainVolumeLast3Hours: Double? = null,
)
