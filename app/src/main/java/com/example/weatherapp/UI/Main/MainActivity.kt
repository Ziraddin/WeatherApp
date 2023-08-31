package com.example.weatherapp.UI.Main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.Adapters.ViewPagerAdapter
import com.example.weatherapp.Constants.Constants.data
import com.example.weatherapp.UI.AddCity.AddCity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 1
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(
            window, false
        )
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getUserLocation()
        setUpAddBtn()
        setUpObservers()
        setUpViewPager()
    }

    fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_REQUEST_CODE
            )
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    viewModel.getCurrentWeatherData(
                        this, it.latitude.toString(), it.longitude.toString()
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            } else {
                Toast.makeText(this@MainActivity, "Permission denied!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setUpObservers() {
        viewModel.result_data.observe(this, Observer {
            setUpViewPager()
        })
    }

    fun setUpViewPager() {
        binding.viewpager.adapter = ViewPagerAdapter(data)
        setUpIndicator()
    }

    fun setUpIndicator() {
        binding.dotsIndicator.attachTo(binding.viewpager)
    }

    private fun setUpAddBtn() {
        val intent = Intent(this, AddCity::class.java)
        binding.addBtn.setOnClickListener {
            startActivity(intent)
            finish()
        }
    }
}
