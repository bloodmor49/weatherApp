package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.data.remote.ApiFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object{
        @Singleton
        @Provides
        fun provideApiFactory() = ApiFactory

        @Singleton
        @Provides
        fun provideFusedLocationProviderClient(application:Application):FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(application)
    }

}