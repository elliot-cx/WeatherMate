package com.elliot.weathermate.views.main

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.elliot.weathermate.BuildConfig
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.WeatherData
import kotlin.math.roundToInt

class WeatherAdapter() : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    lateinit var mClickListener:ItemClickListener
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val inflater = LayoutInflater.from(this.context)
        val eventView = inflater.inflate(R.layout.weather_card,parent,false)
        return ViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Récupération des données
        val weatherData: WeatherData = Utils.weathers[position]
        // Mise en place du nom
        holder.city.text = weatherData.name
        holder.city.isSelected = true
        // On vérifie si il ne s'agit pas d'une météo en mode GPS
        if (weatherData.isGPS){
            holder.city.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.baseline_location_on_24,
                0,
                0,
                0)
        }else{
            holder.city.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                0,
                0)
        }
        // On récupère le temps de la météo pour pouvoir y accéder plus rapidement
        val weather = weatherData.weather[0]

        holder.weather.text = weather.description
        var unitTemp = "C"
        if (Utils.units == "imperial"){unitTemp = "F"}
        holder.temperature.text = "${weatherData.weatherInfo.temp.roundToInt()}°${unitTemp}"

        val weatherName = weather.main.lowercase()
        // Mise en place de l'animation en fonction du temps
        var res = context.resources.getIdentifier(
            "${weatherName}${weather.id}", "raw",
            BuildConfig.APPLICATION_ID)
        // Si la ressource n'est pas trouvée
        if (res == 0){
            res = context.resources.getIdentifier(
                weatherName, "raw",
                BuildConfig.APPLICATION_ID)
        }
        // On configure l'animation
        holder.animation.let {
            it.setAnimation(res)
            it.playAnimation()
        }

        // Mise en place du background en fonction du temps
        val backgroundDrawable = holder.card.background as GradientDrawable
        backgroundDrawable.run {
            mutate()
            colors = Utils.choseWeatherBackground(weatherName)
        }
    }

    override fun getItemCount(): Int {
        return Utils.weathers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val card: LinearLayout
        val city: TextView
        val weather: TextView
        val temperature: TextView
        val animation: LottieAnimationView

        init {
            itemView.setOnClickListener(this)
            card = itemView.findViewById(R.id.card_background)
            city = itemView.findViewById(R.id.city)
            weather = itemView.findViewById(R.id.weather)
            temperature = itemView.findViewById(R.id.temperature)
            animation = itemView.findViewById(R.id.animationView)
        }
        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener.onItemClick(view,absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener!!
    }


    // implémentation pour supporter le click
    // Non finale !
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}

