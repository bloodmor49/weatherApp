package com.example.weatherapp.data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.weatherapp.domain.AppRepository
import com.example.weatherapp.domain.entities.PojoMain
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory,
) : AppRepository {

    //TODO Ошибки сделать через Resource.Success // Error
    //TODO Доработать подгрузку данных через отдельный класс, решить проблему с тасками.

    override suspend fun getWeatherByCity(
        context: Context,
        cityName: String,
        appid: String,
        lang: String,
        units: String,
    ): PojoMain? {

        var result: PojoMain? = null

        try {
            val response = apiFactory.retrofit.getWeatherByCity(cityName, appid, lang, units)
            result = if (response.isSuccessful && response.body() != null)
                response.body()!!
            else null
        } catch (e: Exception) {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }

        return result
    }

    override suspend fun getWeatherByLocation(
        context: Context,
        appid: String,
        lang: String,
        units: String,
    ): PojoMain? {

        var result: PojoMain? = null

        val intent = Intent(context, NavForegroundService::class.java).also {
            context.startService(it)
        }

        var lat = ""
        var lon = ""

        LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {
            lon = String.format("%.1f", it.longitude)
            lat = String.format("%.1f", it.latitude)
            Log.d("LocationResult", "$lat $lon")
        }

        delay(4000)

        intent.also { intent1 ->
            context.stopService(intent1)
        }

        try {
            val response = apiFactory.retrofit.getWeatherByLocation(lat, lon, appid, lang, units)
            result = if (response.isSuccessful && response.body() != null) {
                Log.d("LocationResult", "try $lat $lon")
                response.body()!!
            } else null

        } catch (e: Exception) {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }
        return result
    }
}
