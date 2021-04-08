package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.WeatherInfoEntity
import com.example.weathertoday.domain.models.Weather
import com.example.weathertoday.domain.models.WeatherInfo
import javax.inject.Inject

class WeatherInfoEntityToWeatherInfoMapper @Inject constructor(
    private val cloudEntityMapper: CloudEntityToCloudMapper,
    private val mainInfoEntityMapper: MainInfoEntityToMainInfoMapper,
    private val rainEntityMapper: RainEntityToRainMapper,
    private val snowEntityMapper: SnowEntityToSnowMapper,
    private val weatherEntityMapper: WeatherEntityToWeatherMapper,
    private val windEntityMapper: WindEntityToWindMapper
) :
    Mapper<WeatherInfoEntity, WeatherInfo>() {
    override fun mapFrom(from: WeatherInfoEntity): WeatherInfo {
        val weathers = ArrayList<Weather>()
        for(weatherInfo in from.weather){
            weathers.add(weatherEntityMapper.mapFrom(weatherInfo))
        }
        return WeatherInfo(
            weather = weathers,
            cloud = cloudEntityMapper.mapFrom(from.cloudEntity),
            rain = from.rainEntity?.let { rainEntityMapper.mapFrom(it) },
            snow = from.snowEntity?.let { snowEntityMapper.mapFrom(it) },
            wind = windEntityMapper.mapFrom(from.windEntity),
            main = mainInfoEntityMapper.mapFrom(from.main)

        )
    }
}