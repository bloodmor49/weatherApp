package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.entities.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val appRepository: WeatherRepository,
) {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> =
        appRepository.getWeatherData(lat, long)
}