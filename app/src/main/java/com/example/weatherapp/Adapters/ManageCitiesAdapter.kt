package com.example.weatherapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Constants.Constants.counter
import com.example.weatherapp.Constants.Constants.locations
import com.example.weatherapp.Data.Response.WeatherData
import com.example.weatherapp.R
import kotlin.math.roundToInt

class ManageCitiesAdapter(var data: MutableList<WeatherData>) :
    RecyclerView.Adapter<ManageCitiesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city = itemView.findViewById<TextView>(R.id.city)
        val dailyTemp = itemView.findViewById<TextView>(R.id.dailyTemp)
        val currentTemp = itemView.findViewById<TextView>(R.id.currentTemp)
        val location = itemView.findViewById<ImageView>(R.id.locationMark)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ManageCitiesAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ManageCitiesAdapter.ViewHolder, position: Int) {
        val weatherdata = data[position]
        if (position == 0) {
            holder.location.visibility = View.VISIBLE
        }
        holder.city.text = weatherdata.name
        holder.dailyTemp.text = weatherdata.main.tempMax?.roundToInt()
            .toString() + "° / " + weatherdata.main.tempMin?.roundToInt().toString() + "°"
        holder.currentTemp.text = weatherdata.main.feelsLike?.roundToInt().toString() + "°"

        holder.delete.setOnClickListener {
            data.removeAt(position)
            val cityName = weatherdata.name
            if (locations.contains(cityName)) {
                locations.remove(cityName)
            }
            notifyItemRemoved(position)
            counter = 404
        }
    }
}