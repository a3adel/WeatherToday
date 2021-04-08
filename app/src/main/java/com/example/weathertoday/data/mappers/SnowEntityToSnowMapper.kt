package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.SnowEntity
import com.example.weathertoday.domain.models.Snow
import javax.inject.Inject

class SnowEntityToSnowMapper @Inject constructor() : Mapper<SnowEntity, Snow>() {
    override fun mapFrom(from: SnowEntity): Snow {
        return Snow(
            snowVolumeLastHour = from.snowVolumeLastHour,
            snowVolumeLast3Hours = from.snowVolumeLast3Hours
        )
    }
}