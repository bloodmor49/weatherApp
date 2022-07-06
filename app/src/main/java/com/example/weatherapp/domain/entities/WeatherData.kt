package com.example.weatherapp.domain.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

/** Общая сущность данных по погоде */
data class WeatherData(

    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType
)
