package com.example.weatherapp.domain.entities

/** Наш рабочий класс для визуализации.
 * Включает в себя список данных по погоде за каждый день на неделю с сегодняшнего дня.
 */
data class WeatherInfo(
    val weatherDataPerDay: Map<Int,List<WeatherData>>,
    val currentWeatherData: WeatherData?
)
