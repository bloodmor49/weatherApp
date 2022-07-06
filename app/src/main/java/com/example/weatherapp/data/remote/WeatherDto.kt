package com.example.weatherapp.data.remote

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class WeatherDto(
//    @field:Json(name = "hourly")
    @SerializedName("hourly")
    val weatherData: WeatherDataDto
)
