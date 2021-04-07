package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.RainEntity
import com.example.weathertoday.domain.models.Rain
import javax.inject.Inject

class RainEntityToRainMapper @Inject constructor() : Mapper<RainEntity, Rain>() {
    override fun mapFrom(from: RainEntity): Rain {
        return Rain(
            rainVolumeLastHour = from.rainVolumeLastHour,
            rainVolumeLast3Hours = from.rainVolumeLast3Hours
        )
    }
}