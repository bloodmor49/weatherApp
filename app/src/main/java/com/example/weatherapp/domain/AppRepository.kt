package com.example.weatherapp.domain

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherapp.domain.entities.PojoMain

interface AppRepository {

    suspend fun getWeatherByCity(
        context: Context,
        cityName: String,
        appid: String,
        lang: String,
        units: String,
    ): PojoMain?

    suspend fun getWeatherByLocation(
        context: Context,
        appid: String,
        lang: String,
        units: String
    ): PojoMain?
}