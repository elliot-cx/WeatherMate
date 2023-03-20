package com.elliot.weathermate.views.detail

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.Weather
import com.elliot.weathermate.data.WeatherData
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity() {

    var favorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Récupération des données de l'intent
        val weatherData = intent.getParcelableExtra<WeatherData>("weather")
        val weather: Weather = weatherData!!.weather[0]

        // Mise à jour des données du layout
        var unitTemp = "C"
        if (Utils.units == "imperial"){unitTemp = "F"}
        city_name_text.text = weatherData.name
        time_text.text = Utils.formatDate(Date(weatherData.dt * 1000))
        temp_text.text =  "${weatherData.weatherInfo.temp.roundToInt()}°${unitTemp}"
        weather_text.text = weather.description
        // changement dynamique de l'animation
        animationView.setAnimation(
            resources.getIdentifier(
                "${weather.main.lowercase()}${weather.id}", "raw",
                packageName
            )
        )
        animationView.playAnimation()

        var unitText = "m/s"
        if (Utils.units == "imperial"){unitText = "mph"}
        windSpeed.text = "${weatherData.wind.speed} ${unitText}"
        windOrientation.rotation = weatherData.wind.deg + 180
        humidity_text.text = "${weatherData.weatherInfo.humidity}%"

        // Mise en place du background en fonction du temps
        val backgroundDrawable =  this.gradient_background.background as GradientDrawable
        backgroundDrawable.run {
            mutate()
            colors = Utils.choseWeatherBackground(weather.main.lowercase())
        }


        // Bouton retour
        back_arrow.setOnClickListener{
            this.finish()
        }

        // Bonton Favoris

        if (weatherData.isGPS){
            favoriteButton.visibility = View.GONE
        }

        if(Utils.weathers.contains(weatherData)){
            favorite = true
            favoriteButton.progress = 1.0f
        }

        favoriteButton.setOnClickListener{
            if (this.favorite){
                favoriteButton.frame = 0
                this.favorite = false
                Utils.weathers.remove(weatherData)
            }else{
                favoriteButton.playAnimation()
                this.favorite = true
                Utils.weathers.add(weatherData)
            }
            Utils.saveLocations(this)
        }
    }
}