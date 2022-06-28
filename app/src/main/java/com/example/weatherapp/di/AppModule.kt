package com.example.weatherapp.di

import com.example.weatherapp.data.ApiFactory
import com.example.weatherapp.data.AppRepositoryImpl
import com.example.weatherapp.domain.AppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object{
        @Singleton
        @Provides
        fun provideApiFactory() = ApiFactory
    }

    @Binds
    @Singleton
    fun provideAppRepository(impl: AppRepositoryImpl): AppRepository
}