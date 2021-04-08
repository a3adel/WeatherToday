package com.example.weathertoday.domain.models

import com.example.weathertoday.data.entities.*

data class WeatherInfo(
    val weather: List<Weather>,
    val main: MainInfo,
    val wind: Wind,
    val cloud: Cloud,
    val rain: Rain? = null,
    val snow: Snow? = null,
)
