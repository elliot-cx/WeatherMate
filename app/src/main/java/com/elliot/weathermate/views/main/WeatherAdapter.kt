package com.elliot.weathermate.views.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.elliot.weathermate.BuildConfig
import com.elliot.weathermate.R
import com.elliot.weathermate.data.WeatherData

class WeatherAdapter(var weatherList: MutableList<WeatherData>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    lateinit var mClickListener:ItemClickListener
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val inflater = LayoutInflater.from(this.context)
        val eventView = inflater.inflate(R.layout.weather_card,parent,false)
        return ViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val weatherData: WeatherData = weatherList[position]

        holder.city.text = weatherData.name

        val weather = weatherData.weather[0]

        holder.weather.text = weather.description
        holder.temperature.text = "${weatherData.weatherInfo.temp}°"

        holder.animation.setAnimation(
            context.resources.getIdentifier(
                "${weather.main.lowercase()}${weather.id}", "raw",
                BuildConfig.APPLICATION_ID
            )
        )
        holder.animation.playAnimation()
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val city: TextView
        val weather: TextView
        val temperature: TextView
        val animation: LottieAnimationView

        init {
            city = itemView.findViewById(R.id.city)
            weather = itemView.findViewById(R.id.weather)
            temperature = itemView.findViewById(R.id.temperature)
            animation = itemView.findViewById(R.id.animationView)
        }
        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener.onItemClick(view,absoluteAdapterPosition)
        }
    }

    fun getItem(id: Int): WeatherData {
        return weatherList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener!!
    }

    // implémentation pour supporter le click
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}

