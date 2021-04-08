package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.WeatherEntity
import com.example.weathertoday.data.remote.ApiConstants
import com.example.weathertoday.domain.models.Weather
import javax.inject.Inject

class WeatherEntityToWeatherMapper @Inject constructor() : Mapper<WeatherEntity, Weather>() {
    override fun mapFrom(from: WeatherEntity): Weather {
        return Weather(
            main = from.main,
            description = from.description,
            iconUrl = getWeatherIconUrl(from.iconId)
        )
    }

    private fun getWeatherIconUrl(iconId: String?) = "${ApiConstants.BASE_ICON_URL}$iconId.png"
}