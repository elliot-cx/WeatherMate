package com.elliot.weathermate.data

import android.view.View
import com.elliot.weathermate.data.WeatherAPIService.getWeather
import com.google.android.material.snackbar.Snackbar
import com.google.gson.annotations.SerializedName
import java.util.*

// Data classe regroupant toutes les informations Météorologique
data class WeatherData(
    var weather: List<Weather>,
    @SerializedName("main")
    var weatherInfo: WeatherInfo,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Long,
    var name: String,
    val coord: Coord,
    val isGPS: Boolean = false
){
    override fun toString(): String {
        return """
            Nom : ${this.name}
            Météo : ${this.weatherInfo}
            Vents : ${this.wind}
            Nuages : ${this.clouds.all}
            Date : ${Date((this.dt + 3600) * 1000)}
            Location : ${this.coord}
            GPS : ${this.isGPS}
        """.trimIndent()
    }

    fun update(view: View) {
        getWeather(name,{
            this.weather = it.weather
            this.weatherInfo = it.weatherInfo
            this.wind = it.wind
            this.clouds = it.clouds
            this.dt = it.dt
            this.name = it.name
        },{ Snackbar.make(view,"Erreur de connexion", Snackbar.LENGTH_LONG).show()})
    }
}

data class WeatherInfo(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int,
){
    override fun toString(): String {
        return """
            Température: ${this.temp}°
            Température Réel: ${this.feels_like}°
            Température minimale : ${this.temp_min}°
            Température maximale : ${this.temp_max}°
            Pression : ${this.pressure} mpa
            Humidité : ${this.humidity}%
        """.trimIndent()
    }
}

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Float,
    val deg: Float
){
    override fun toString(): String {
        return """
            Vitesse : ${this.speed} m/s
            Orientation : ${this.deg}°
        """.trimIndent()
    }
}

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Coord(
    val lon: Float,
    val lat: Float
){
    override fun toString(): String {
        return """
            lon : ${this.lon}
            lat : ${this.lat}
        """.trimIndent()
    }
}


