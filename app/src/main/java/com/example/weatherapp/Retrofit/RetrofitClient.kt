package com.example.weatherapp.Retrofit

import com.example.weatherapp.Api.IApi
import com.example.weatherapp.Constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getWeatherData(): IApi {
        val retrofitInstance = Retrofit.Builder().baseUrl(Constants.BASE_URL_WEATHER_DATA)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofitInstance.create(IApi::class.java)
    }

    fun getWeatherImage(): IApi {
        val retrofitInstance = Retrofit.Builder().baseUrl(Constants.BASE_URL_WEATHER_IMAGE)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofitInstance.create(IApi::class.java)
    }
}
