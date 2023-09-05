package com.example.weatherapp.UI.Main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Constants.Constants
import com.example.weatherapp.Constants.Constants.data
import com.example.weatherapp.Constants.Constants.locations
import com.example.weatherapp.Data.Response.WeatherData
import com.example.weatherapp.Data.Response.WeatherImage
import com.example.weatherapp.Retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var result_data = MutableLiveData<WeatherData>()
    var lastData = MutableLiveData<WeatherData>()

    fun getCurrentWeatherData(context: Context, lat: String, lon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.getWeatherData()
                .getLocationWeatherData(lat, lon, Constants.API_KEY_WEATHER_DATA, "metric")
                ?.enqueue(object : Callback<WeatherData?> {

                    override fun onResponse(
                        call: Call<WeatherData?>, response: Response<WeatherData?>
                    ) {
                        val res = response.body() as WeatherData
                        if (res !in data) {
                            data.add(0, res)
                            result_data.postValue(res)
                        }
                    }

                    override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                        Toast.makeText(context, "Failed to get data!", Toast.LENGTH_SHORT).show()
                    }
                })
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
                    if (res.name !in locations) {
                        locations.add(res.name.toString())
                        data.add(res)
                        lastData.postValue(res)
                    }
                }

                override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                    Toast.makeText(context, "Failed to get data!", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getWeatherImage(context: Context, condition: String) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.getWeatherImage()
                .getWeatherImage(condition, Constants.API_KEY_WEATHER_IMAGE)
                ?.enqueue(object : Callback<WeatherImage?> {

                    override fun onResponse(
                        call: Call<WeatherImage?>, response: Response<WeatherImage?>
                    ) {
                        val res = response.body() as WeatherImage
                        result_data.value?.weatherImage?.raw = res.raw
                    }

                    override fun onFailure(call: Call<WeatherImage?>, t: Throwable) {
                        Toast.makeText(context, "Failed to get Image!", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}

