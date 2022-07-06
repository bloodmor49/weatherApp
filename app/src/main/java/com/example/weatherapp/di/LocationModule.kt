package com.example.weatherapp.di

import com.example.weatherapp.data.repositoryImpl.LocationTrackerImpl
import com.example.weatherapp.domain.repository.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    @Singleton
    fun bindLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTracker

}