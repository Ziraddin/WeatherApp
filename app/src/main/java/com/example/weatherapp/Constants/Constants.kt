package com.example.weatherapp.Constants

import com.example.weatherapp.Data.Response.WeatherData

object Constants {
    const val API_KEY_WEATHER_DATA = "7d7d4ae62833de788280cc8fc4d42663"
    const val API_KEY_WEATHER_IMAGE = "slO4h1EqyF1Lebmx0O7XaQ9kwzZLvekl7nxQCAjoM4E"
    const val BASE_URL_WEATHER_DATA = "https://api.openweathermap.org/"
    const val BASE_URL_WEATHER_IMAGE = "https://api.unsplash.com/"
    const val IMAGE_URL = "https://openweathermap.org/img/wn/"

    var data = mutableListOf<WeatherData>()
}