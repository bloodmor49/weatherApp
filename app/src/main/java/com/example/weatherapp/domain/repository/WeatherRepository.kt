package com.example.weatherapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.domain.entities.WeatherInfo
import com.example.weatherapp.domain.util.Resource


interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}