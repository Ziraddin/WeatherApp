package com.example.weatherapp.UI.Main

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.Adapters.ViewPagerAdapter
import com.example.weatherapp.Constants.Constants.counter
import com.example.weatherapp.Constants.Constants.data
import com.example.weatherapp.Constants.Constants.locations
import com.example.weatherapp.R
import com.example.weatherapp.UI.AddCity.AddCity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 1
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)


        getUserLocation()
        when (counter) {
            404 -> saveLocations()
            0 -> getLocations()
            else -> {
                locations.addAll(sharedPreferences.getStringSet("locations", emptySet())!!)
                saveLocations()
            }
        }
        setUpAddBtn()
        setUpObservers()
        setUpViewPager()
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                playVideo(position)
            }
        })
    }

    fun playVideo(position: Int) {
        val video = binding.conditionVideo
        val videoResId = when (data[position].weather[0].description) {
            "scattered clouds" -> R.raw.partlycloudly
            "broken clouds" -> R.raw.partlycloudly
            "few clouds" -> R.raw.cloudy
            "clear sky" -> R.raw.sunny
            "thunderstorm" -> R.raw.stormy
            else -> R.raw.cloudy
        }
        val videoUri = Uri.parse("android.resource://${packageName}/${videoResId}")
        video.setVideoURI(videoUri)
        video.start()
        video.setOnCompletionListener { it ->
            it.start()
        }
    }

    fun saveLocations() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("locations", locations)
        editor.apply()
    }

    fun getLocations() {
        val lastData = sharedPreferences.getStringSet("locations", null)
        if (lastData != null) {
            for (i in lastData) {
                viewModel.getCityWeatherData(i, this)
            }
        }
    }

    class CubeInRotationTransformation : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            if (position < 0) {
                page.rotationY = 25 * abs(position)
            } else {
                page.rotationY = -25 * abs(position)
            }
        }
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
        viewModel.lastData.observe(this, Observer {
            setUpViewPager()
        })
    }

    fun setUpViewPager() {
        val viewpager = binding.viewpager
        val cubeOutRotationTransformer = CubeInRotationTransformation()
        viewpager.adapter = ViewPagerAdapter(data)
        viewpager.setPageTransformer(cubeOutRotationTransformer)
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

    override fun onDestroy() {
        super.onDestroy()
        counter++
    }
}
