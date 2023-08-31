package com.example.weatherapp.Data.Response

import com.google.gson.annotations.SerializedName

data class WeatherData(
    var name: String?,
    var weather: MutableList<Weather>,
    var main: Main,
    var weatherImage: WeatherImage
)

data class Weather(
    var description: String, var icon: String?
)

data class Main(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("temp_min") val tempMin: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("humidity") val humidity: Int?
)

data class Wind(
    @SerializedName("speed") val speed: Int?
)