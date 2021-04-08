package com.example.weathertoday.data.mappers

import com.example.weathertoday.data.base.Mapper
import com.example.weathertoday.data.entities.CloudEntity
import com.example.weathertoday.domain.models.Cloud
import javax.inject.Inject

class CloudEntityToCloudMapper @Inject constructor():Mapper<CloudEntity, Cloud>() {
    override fun mapFrom(from: CloudEntity): Cloud {
        return Cloud(cloudsPercentage = from.all/100)
    }
}