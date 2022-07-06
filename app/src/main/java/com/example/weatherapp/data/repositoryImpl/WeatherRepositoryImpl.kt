package com.example.weatherapp.data.repositoryImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.mappers.toWeatherInfo
import com.example.weatherapp.data.remote.ApiFactory
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.entities.WeatherInfo
import com.example.weatherapp.domain.util.Resource
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory,
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(data = apiFactory.retrofit.getWeatherData(lat = lat, long = long)
                .toWeatherInfo())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("LocationResult", "Error in weatherRepoImpl ${e.message} ")
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }
    }

}
