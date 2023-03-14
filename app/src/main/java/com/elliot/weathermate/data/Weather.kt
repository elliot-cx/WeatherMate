package com.elliot.weathermate.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

// Data classe regroupant toutes les informations Météorologique
@Parcelize
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
) : Parcelable {
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
}

@Parcelize
data class WeatherInfo(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int,
) : Parcelable{
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

@Parcelize
data class Clouds(
    val all: Int
) : Parcelable

@Parcelize
data class Wind(
    val speed: Float,
    val deg: Float
) : Parcelable{
    override fun toString(): String {
        return """
            Vitesse : ${this.speed} m/s
            Orientation : ${this.deg}°
        """.trimIndent()
    }
}

@Parcelize
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable

@Parcelize
data class Coord(
    val lon: Float,
    val lat: Float
) : Parcelable {
    override fun toString(): String {
        return """
            lon : ${this.lon}
            lat : ${this.lat}
        """.trimIndent()
    }
}


