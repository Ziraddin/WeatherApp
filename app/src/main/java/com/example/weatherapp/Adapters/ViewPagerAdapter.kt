package com.example.weatherapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Constants.Constants
import com.example.weatherapp.Data.Response.WeatherData
import com.example.weatherapp.R
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class ViewPagerAdapter(var data: MutableList<WeatherData>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var location = itemView.findViewById<TextView>(R.id.locationTv)
        var desc = itemView.findViewById<TextView>(R.id.descTV)
        var temp = itemView.findViewById<TextView>(R.id.tempTv)
        var tempMin = itemView.findViewById<TextView>(R.id.tempMinTv)
        var tempMax = itemView.findViewById<TextView>(R.id.tempMaxTv)
        var pressure = itemView.findViewById<TextView>(R.id.pressureTv)
        var humidity = itemView.findViewById<TextView>(R.id.humidityTv)
        var conditionIcon = itemView.findViewById<ImageView>(R.id.conditionIcon)
        var locationArrow = itemView.findViewById<ImageView>(R.id.locationArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_pager_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWeather = data[position]
        if (position == 0) {
            holder.locationArrow.visibility = View.VISIBLE
        }
        holder.location.text = currentWeather.name
        holder.temp.text = currentWeather.main.feelsLike?.roundToInt().toString()
        holder.desc.text = currentWeather.weather[0].description
        holder.tempMin.text = "Min Temp: ${currentWeather.main.tempMin} °C"
        holder.tempMax.text = "Max Temp: ${currentWeather.main.tempMax} °C"
        holder.pressure.text = "Pressure: ${currentWeather.main.pressure} hPa"
        holder.humidity.text = "Humidity: ${currentWeather.main.humidity}%"

        Picasso.get().load(Constants.IMAGE_URL + currentWeather.weather[0].icon + "@4x.png")
            .into(holder.conditionIcon)
    }
}