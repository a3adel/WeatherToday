package com.example.weathertoday.domain.dataSources

import com.example.weathertoday.data.entities.Resource
import com.example.weathertoday.domain.models.WeatherInfo

interface GeoWeatherRepo {
    suspend fun getGeoWeather(lat:Double,lon:Double): Resource<WeatherInfo>
}
