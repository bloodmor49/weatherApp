package com.example.weatherapp.data.repositoryImpl

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weatherapp.domain.repository.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application,
) : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {

//      Проверка разрешений.

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

//      Проблема с корутинами для FusedLocationProviderClient решается следующим образом.
//      Корутины позволяют работать с коллбэками - они преобразуются в саспенд корутину.
//      Т.е. мы работаем с тасками. По результата таска мы либо продолжаем приоставновленную
//      корутину или же останаилваем при неудаче.

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                //Мы получаем таск и по его результату продолжаем корутину.
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it)
                    Log.d("LocationResult", "Success lat = ${it.latitude}, long = ${it.longitude}")
                }
                addOnFailureListener {
                    cont.resume(null)
                    Log.d("LocationResult", "Failure")
                }
                addOnCanceledListener {
                    cont.cancel()
                    Log.d("LocationResult", "Cancel")
                }
            }
        }
    }
}