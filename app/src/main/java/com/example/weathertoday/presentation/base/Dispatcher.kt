package com.example.weathertoday.presentation.base

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}