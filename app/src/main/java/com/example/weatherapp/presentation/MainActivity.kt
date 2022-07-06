package com.example.weatherapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
) : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var weatherAdapter: WeatherAdapter

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissions()
        setUpViewModel()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        with(binding.resView) {
            this?.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            this?.adapter = weatherAdapter
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        requestPermissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadWeatherInfo()

        viewModel.weatherState.observe(this) { result ->
            val currentWeather = result?.currentWeatherData
            with(binding) {
                weatherAdapter.weatherListToday =
                    result.weatherDataPerDay[0] ?: emptyList()
                textViewDescriptionSet?.text =
                    currentWeather?.weatherType?.weatherDesc
                textViewDateTime?.text = "${currentWeather?.time}"
                textViewDegree?.text =
                    "${currentWeather?.temperatureCelsius} °C"
                textViewPressure?.text = "${currentWeather?.pressure} Hg "
                textViewWet?.text = "${currentWeather?.humidity} %"
                textViewWind?.text = "${currentWeather?.windSpeed} м/с"
                imageView?.setImageResource(currentWeather?.weatherType!!.iconRes)
            }
        }
    }
}