package com.example.weathertoday.data.repos.geoWeather

import android.content.Context
import com.example.weathertoday.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GeoWeatherErrorManager @Inject constructor(@ApplicationContext private val context: Context) {
    fun getErrorMessage(key: String): String {
        if (errorsMap.containsKey(key))
            if (errorsMap[key] != null)
                return errorsMap[key]!!
        return context.getString(R.string.default_error)
    }

    private val errorsMap: Map<String, String>
        get() = mapOf(
            Pair(GeoWeatherErrorCauses.NETWORK_ERROR, context.getString(R.string.no_network)),
            Pair(GeoWeatherErrorCauses.REQUEST_ERROR, context.getString(R.string.default_error))
        )
}