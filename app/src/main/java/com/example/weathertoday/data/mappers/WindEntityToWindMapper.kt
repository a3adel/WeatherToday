package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.WindEntity
import com.example.weathertoday.domain.models.Wind
import javax.inject.Inject

class WindEntityToWindMapper @Inject constructor() : Mapper<WindEntity, Wind>() {
    override fun mapFrom(from: WindEntity): Wind {
        return Wind(
            speed = from.speed,
            degree = from.degree
        )
    }
}