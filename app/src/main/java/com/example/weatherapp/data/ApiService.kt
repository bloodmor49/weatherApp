package com.example.weatherapp.data

import com.example.weatherapp.domain.entities.PojoMain
import com.example.weatherapp.other.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Интерфейс работы с JSON (Retrofit).
 */
interface ApiService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") appid: String,
        @Query("lang") lang: String,
        @Query("units") units: String,
    ): Response<PojoMain>

    @GET("/data/2.5/weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("lang") lang: String,
        @Query("units") units: String,
    ): Response<PojoMain>
}