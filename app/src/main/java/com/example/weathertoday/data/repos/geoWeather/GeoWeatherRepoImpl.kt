package com.example.weathertoday.data.repos.geoWeather

import com.example.weathertoday.data.entities.Resource
import com.example.weathertoday.domain.dataSources.GeoWeatherRepo
import com.example.weathertoday.domain.models.WeatherInfo
import javax.inject.Inject

class GeoWeatherRepoImpl @Inject constructor(private val geoWeatherRemoteClient: WeatherRemoteClient) :
    GeoWeatherRepo {
    override suspend fun getGeoWeather(lat: Double, lon: Double): Resource<WeatherInfo> {
        return geoWeatherRemoteClient.getWeatherForLocation(lat, lon)
    }
}