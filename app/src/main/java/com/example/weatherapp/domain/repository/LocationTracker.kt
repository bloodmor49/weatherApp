package com.example.weatherapp.domain.repository

import android.location.Location

/** Определяем местоположение */
interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}