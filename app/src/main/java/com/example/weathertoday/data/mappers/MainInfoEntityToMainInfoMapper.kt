package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.MainInfoEntity
import com.example.weathertoday.domain.models.MainInfo
import javax.inject.Inject

class MainInfoEntityToMainInfoMapper @Inject constructor() : Mapper<MainInfoEntity, MainInfo>() {
    override fun mapFrom(from: MainInfoEntity): MainInfo {
        return MainInfo(
            maxTemp = from.maxTemp,
            minTemp = from.minTemp,
            humidity = from.humidity,
            pressure = from.pressure,
            feelsLike = from.feelsLike,
            temp = from.temp
        )
    }
}