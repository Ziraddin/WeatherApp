package com.example.weatherapp.Api

import com.example.weatherapp.Data.Response.WeatherData
import com.example.weatherapp.Data.Response.WeatherImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {
    @GET("data/2.5/weather")
    fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") apikey: String,
        @Query("units") units: String,
    ): Call<WeatherData?>?

    @GET("data/2.5/weather")
    fun getLocationWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apikey: String,
        @Query("units") units: String,
    ): Call<WeatherData?>?

    @GET("photos/random")
    fun getWeatherImage(
        @Query("query") condition: String, @Query("client_id") apikey: String
    ): Call<WeatherImage?>?
}