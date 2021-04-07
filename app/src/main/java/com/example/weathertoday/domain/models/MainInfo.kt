package com.example.weathertoday.domain.models

data class MainInfo(
    val temp: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val pressure: Double,
    val humidity: Double
)
