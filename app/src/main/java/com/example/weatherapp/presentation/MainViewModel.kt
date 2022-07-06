package com.example.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entities.WeatherInfo
import com.example.weatherapp.domain.usecases.GetLocationUseCase
import com.example.weatherapp.domain.usecases.GetWeatherDataUseCase
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val getLocationUseCase: GetLocationUseCase,
) : ViewModel() {

    private var _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherState: LiveData<WeatherInfo>
        get() = _weatherInfo

    fun loadWeatherInfo() {
        viewModelScope.launch {
            getLocationUseCase.getCurrentLocation()?.let {
                when (val result =
                    getWeatherDataUseCase.getWeatherData(it.longitude, it.latitude)) {
                    is Resource.Success -> {
                        _weatherInfo.value = result.data!!
                        Log.d("LocationResult", "Success ${result.data}")
                    }
                    is Resource.Error -> {
                        Log.d("LocationResult", "Error ${result.message}")
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
