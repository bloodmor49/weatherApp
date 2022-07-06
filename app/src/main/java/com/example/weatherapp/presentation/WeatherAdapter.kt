package com.example.weatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeatherCardBinding
import com.example.weatherapp.domain.entities.WeatherData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherAdapter @Inject constructor() : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(val binding: WeatherCardBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var weatherListToday: List<WeatherData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(WeatherCardBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherListToday[position]
        with(holder.binding) {
            textView.text = String.format( "%02d:%02d", weather.time.hour, weather.time.minute)
            imageView2.setImageResource(weather.weatherType.iconRes)
            textView2.text = weather.temperatureCelsius.toString()
        }
    }

    override fun getItemCount(): Int {
        return weatherListToday.size
    }
}