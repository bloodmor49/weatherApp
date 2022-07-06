package com.example.weatherapp.data.remote

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class WeatherDataDto(
    val time: List<String>,
//    @field:Json(name = "temperature_2m")
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
//    @field:Json(name = "weathercode")
    @SerializedName("weathercode")
    val weatherCodes: List<Int>,
//    @field:Json(name = "pressure_msl")
    @SerializedName("pressure_msl")
    val pressures: List<Double>,
//    @field:Json(name = "windspeed_10m")
    @SerializedName("windspeed_10m")
    val windSpeeds: List<Double>,
//    @field:Json(name = "relativehumidity_2m")
    @SerializedName("relativehumidity_2m")
    val humidities: List<Double>
)
