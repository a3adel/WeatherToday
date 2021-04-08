package com.example.weathertoday.presentation.HomeActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weathertoday.data.entities.Resource
import com.example.weathertoday.domain.models.WeatherInfo
import com.example.weathertoday.domain.useCases.GetGeoWeatherUseCase
import com.example.weathertoday.presentation.base.BaseViewModel
import com.example.weathertoday.presentation.base.Dispatcher
import com.example.weathertoday.presentation.base.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGeoWeatherUseCase: GetGeoWeatherUseCase,
    private val dispatcher: Dispatcher
) : BaseViewModel() {
    private val weatherMutableLiveData = MutableLiveData<WeatherInfo>()
    val weatherLiveData: LiveData<WeatherInfo> get() = weatherMutableLiveData
    fun getGeoWeather(lat: Double, lon: Double) {
        showProgressBarMutableLiveData.postValue( (SingleEvent(Unit)))

        viewModelScope.launch(dispatcher.io()) {
            when (val resource = getGeoWeatherUseCase.getWeather(lat, lon)) {
                is Resource.Success -> {
                    hideProgressBarMutableLiveData.postValue( (SingleEvent(Unit)))

                    resource.data.let {
                        weatherMutableLiveData.postValue( it) }
                }
                is Resource.Error -> {
                    hideProgressBarMutableLiveData.postValue( (SingleEvent(Unit)))
                    toastMutableLiveData.postValue(SingleEvent(resource.message))
                }
            }
        }
    }
}