package com.example.weatherapp.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.weatherapp.other.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
) : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissions()
        setUpViewModel()
        setUpButtons()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.pojoMain.observe(this) {
            if (it != null) {
                binding.textViewDegreeSet?.text = "${it.main.temp} C"
                binding.textViewCityNameSet?.text = it.name
                binding.textViewDescriptionSet?.text = it.weather[0].description
                binding.editTextTextPersonName.setText(it.name)
            } else Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpButtons() {
        binding.buttonCity?.setOnClickListener {
            viewModel.getWeatherByCityUseCase(this, binding.editTextTextPersonName.text.trim().toString())
        }

        binding.buttonLocation.setOnClickListener {
            viewModel.getWeatherByLocation(this)
        }
    }

    /** Запрос разрешения на получения гео-данных */
    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(this)) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "Нужны разрешения на геолокацию",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Нужны разрешения на геолокацию",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}