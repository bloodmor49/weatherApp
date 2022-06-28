package com.example.weatherapp.domain.usecases

import android.content.Context
import com.example.weatherapp.domain.AppRepository
import com.example.weatherapp.domain.entities.PojoMain
import com.example.weatherapp.other.Constants.API_KEY
import com.example.weatherapp.other.Constants.LANG
import com.example.weatherapp.other.Constants.UNITS
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    suspend fun getWeatherByCity(context: Context, cityName: String): PojoMain? =
        appRepository.getWeatherByCity(context, cityName, API_KEY, LANG, UNITS)
}