package com.example.weatherapp.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entities.PojoMain
import com.example.weatherapp.domain.usecases.GetWeatherByCityUseCase
import com.example.weatherapp.domain.usecases.GetWeatherByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherByCityUsecase: GetWeatherByCityUseCase,
    private val getWeatherByCoordinatesUseCase: GetWeatherByLocationUseCase
) : ViewModel() {

    var pojoMain = MutableLiveData<PojoMain?>()

    fun getWeatherByCityUseCase(context: Context, cityName: String){
        viewModelScope.launch {
            pojoMain.postValue(getWeatherByCityUsecase.getWeatherByCity(context, cityName))
        }
    }

    fun getWeatherByLocation(context: Context) {
        viewModelScope.launch {
            pojoMain.postValue(getWeatherByCoordinatesUseCase.getWeatherByLocation(context))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


}