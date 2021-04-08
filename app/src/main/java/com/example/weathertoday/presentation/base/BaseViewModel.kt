package com.example.weathertoday.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val showProgressBarMutableLiveData = MutableLiveData<SingleEvent<Unit>>()
    val showProgressBarLiveData: LiveData<SingleEvent<Unit>> get() = showProgressBarMutableLiveData

    protected val hideProgressBarMutableLiveData = MutableLiveData<SingleEvent<Unit>>()
    val hideProgressBarLiveData: LiveData<SingleEvent<Unit>> get() = hideProgressBarMutableLiveData

    protected val toastMutableLiveData = MutableLiveData<SingleEvent<String>>()
    val toastLiveData: LiveData<SingleEvent<String>> get() = toastMutableLiveData
}