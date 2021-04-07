package com.example.weathertoday.data.base

abstract class Mapper<T, E> {
    abstract fun mapFrom(from: T): E
}