package com.example.weathertoday.data.entities

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String, val cause: String) : Resource<T>()
}