package com.elliot.weathermate.views.detail

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.Weather
import com.elliot.weathermate.data.WeatherData
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity() {

    var favorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val weatherData = intent.getParcelableExtra<WeatherData>("weather")

        city_name_text.text = weatherData!!.name

        temp_text.text =  "${weatherData.weatherInfo.temp.roundToInt()}°c"

        val weather: Weather = weatherData!!.weather[0]
        weather_text.text = "${weather.description}"

        animationView.setAnimation(
            resources.getIdentifier(
                "${weather.main.lowercase()}${weather.id}", "raw",
                packageName
            )
        )
        animationView.playAnimation()

        windSpeed.text = "${weatherData.wind.speed} m/s"
        windOrientation.rotation = weatherData.wind.deg + 180

        humidity_text.text = "${weatherData.weatherInfo.humidity}%"

        // Mise en place du background en fonction du temps
        val backgroundDrawable =  this.gradient_background.background as GradientDrawable
        backgroundDrawable.run {
            mutate()
            colors = Utils.choseWeatherBackground(weather.main.lowercase())
        }

        if(Utils.weathers.contains(weatherData)){
            favorite = true
        }
    }
}