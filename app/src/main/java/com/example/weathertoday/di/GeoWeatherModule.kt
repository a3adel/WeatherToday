package com.example.weathertoday.di

import com.example.weathertoday.data.remote.GeoWeatherInterface
import com.example.weathertoday.data.repos.geoWeather.GeoWeatherRepoImpl
import com.example.weathertoday.domain.dataSources.GeoWeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class GeoWeatherModule {
    @Provides
    @ViewModelScoped
    fun provideWeatherApiInterface(retrofit: Retrofit):GeoWeatherInterface =
        retrofit.create(GeoWeatherInterface::class.java)

    @Provides
    @ViewModelScoped
    fun provideGeoWeatherRepo(geoWeatherRepoImpl: GeoWeatherRepoImpl): GeoWeatherRepo =
        geoWeatherRepoImpl
}