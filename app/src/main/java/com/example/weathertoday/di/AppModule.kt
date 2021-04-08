package com.example.weathertoday.di

import com.example.weathertoday.data.remote.ApiConstants
import com.example.weathertoday.data.utils.NetworkConnectivity
import com.example.weathertoday.data.utils.NetworkConnectivityImpl
import com.example.weathertoday.presentation.base.Dispatcher
import com.example.weathertoday.presentation.base.DispatcherImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder().build()


    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideDispatcher(dispatcherImpl: DispatcherImpl): Dispatcher = dispatcherImpl

    @Singleton
    @Provides
    fun provideNetworkConnectivity(networkConnectivityImpl: NetworkConnectivityImpl): NetworkConnectivity = networkConnectivityImpl
}