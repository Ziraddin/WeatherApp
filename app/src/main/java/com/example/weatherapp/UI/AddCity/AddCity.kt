package com.example.weatherapp.UI.AddCity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.weatherapp.Constants.Constants
import com.example.weatherapp.Constants.Constants.data
import com.example.weatherapp.Constants.Constants.locations
import com.example.weatherapp.Data.Response.WeatherData
import com.example.weatherapp.Retrofit.RetrofitClient
import com.example.weatherapp.UI.Main.MainActivity
import com.example.weatherapp.databinding.ActivityAddCityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCity : AppCompatActivity() {
    lateinit var binding: ActivityAddCityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(
            window, false
        )
        binding = ActivityAddCityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchCity()
        goBack()
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        binding.backIcon.setOnClickListener {
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun searchCity() {
        binding.searchCityEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = binding.searchCityEditText.compoundDrawables[2]
                if (drawable != null && event.rawX >= binding.searchCityEditText.right - drawable.bounds.width() - 50) {
                    var cityName = binding.searchCityEditText.text.toString().trim().lowercase()
                    if (cityName.isNotEmpty()) {
                        getCityWeatherData(cityName, this)
                        locations.add(cityName)
                    } else {
                        Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            false
        }
    }

    fun getCityWeatherData(cityName: String, context: Context) {
        RetrofitClient.getWeatherData()
            .getWeatherData(cityName, Constants.API_KEY_WEATHER_DATA, "metric")
            ?.enqueue(object : Callback<WeatherData?> {

                override fun onResponse(
                    call: Call<WeatherData?>, response: Response<WeatherData?>
                ) {
                    val res = response.body() as WeatherData
                    if (res !in data) {
                        data.add(res)
                        binding.searchCityEditText.text.clear()
                    } else {
                        Toast.makeText(
                            context,
                            "$cityName is already added",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                    Toast.makeText(context, "Failed to get data!", Toast.LENGTH_SHORT).show()
                }
            })
    }
}