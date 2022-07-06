package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.data.remote.WeatherDto
import com.example.weatherapp.domain.entities.WeatherData
import com.example.weatherapp.domain.entities.WeatherInfo
import com.example.weatherapp.domain.entities.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        //Сначала по индексу определяем параметры.
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        //Далее составляем класс данных от индекса. data[0], data[1] и тд.
        // data[0] - сегодняшний день.
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                //Приводим к формату время.
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
        //Далее делим на 24 часа, чтобы получить прогноз на начало дня.
    }.groupBy {
        it.index / 24
        //По результату получим 7 дней, которые представляем к WeatherData из IndexedWeatherData.
    }.mapValues {
        it.value.map { it.data }
    }.also { print(it.keys) }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val timeNow = LocalDateTime.now()
    //Смотрим ближайшее время, чтобы определить погоду сейчас.
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (timeNow.minute < 30) timeNow.hour else timeNow.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}