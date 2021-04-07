package com.example.weathertoday.data.repos.geoWeather

import com.example.weathertoday.data.entities.Resource
import com.example.weathertoday.data.mappers.WeatherInfoEntityToWeatherInfoMapper
import com.example.weathertoday.data.remote.GeoWeatherInterface
import com.example.weathertoday.data.repos.geoWeather.GeoWeatherErrorCauses.NETWORK_ERROR
import com.example.weathertoday.data.repos.geoWeather.GeoWeatherErrorCauses.REQUEST_ERROR
import com.example.weathertoday.data.utils.NetworkConnectivity
import com.example.weathertoday.domain.models.WeatherInfo
import javax.inject.Inject

class WeatherRemoteClient @Inject constructor(
    private val geoWeatherApiInterface: GeoWeatherInterface,
    private val weatherInfoMapper: WeatherInfoEntityToWeatherInfoMapper,
    private val geoWeatherErrorManager: GeoWeatherErrorManager,
    private val connectivity: NetworkConnectivity
) {
    suspend fun getWeatherForLocation(
        lat: Double,
        lon: Double
    ): Resource<WeatherInfo> {
        return if (connectivity.isConnected()) {
            val response = geoWeatherApiInterface.getGeoWeather(lat = lat, lon = lon)
            if (response.isSuccessful) {
                response.body()?.let { weatherInfoEntity ->
                    Resource.Success(
                        weatherInfoMapper.mapFrom(weatherInfoEntity)
                    )
                } ?: run {
                    Resource.Error(
                        geoWeatherErrorManager.getErrorMessage(REQUEST_ERROR),
                        REQUEST_ERROR
                    )
                }
            } else {
                Resource.Error(
                    geoWeatherErrorManager.getErrorMessage(REQUEST_ERROR),
                    REQUEST_ERROR
                )
            }
        } else {
            Resource.Error(
                geoWeatherErrorManager.getErrorMessage(GeoWeatherErrorCauses.NETWORK_ERROR),
                NETWORK_ERROR
            )
        }


    }
}

