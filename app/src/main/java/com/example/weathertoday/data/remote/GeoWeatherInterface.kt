package com.example.weathertoday.data.remote

import com.example.weathertoday.BuildConfig
import com.example.weathertoday.data.entities.WeatherInfoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoWeatherInterface {
    @GET("data/2.5/weather")
    fun getGeoWeather(
        @Query(ApiConstants.APP_ID_KEY) appKey: String = BuildConfig.API_KEY,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ):Response<WeatherInfoEntity>
}