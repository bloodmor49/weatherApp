package com.example.weatherapp.domain.usecases

import android.location.Location
import com.example.weatherapp.domain.entities.WeatherInfo
import com.example.weatherapp.domain.repository.LocationTracker
import com.example.weatherapp.domain.util.Resource
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationTracker: LocationTracker
) {
    suspend fun getCurrentLocation(): Location? = locationTracker.getCurrentLocation()
}