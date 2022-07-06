package com.example.weatherapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Чтоб знать откуда брать application context */
@HiltAndroidApp
class HiltApplication : Application() {
}