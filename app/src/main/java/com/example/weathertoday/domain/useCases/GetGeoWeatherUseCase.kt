package com.example.weathertoday.domain.useCases

import com.example.weathertoday.data.entities.Resource
import com.example.weathertoday.domain.dataSources.GeoWeatherRepo
import com.example.weathertoday.domain.models.MainInfo
import com.example.weathertoday.domain.models.WeatherInfo
import javax.inject.Inject

class GetGeoWeatherUseCase @Inject constructor(private val geoWeatherRepo: GeoWeatherRepo) {
    suspend fun getWeather(lat: Double, lon: Double): Resource<WeatherInfo> {
        return geoWeatherRepo.getGeoWeather(lat,lon)
    }
}