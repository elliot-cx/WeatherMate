package com.elliot.weathermate.data

import com.google.gson.annotations.SerializedName

data class WeatherData(
    val weather: List<Weather>,
    @SerializedName("main")
    val weatherInfo: WeatherInfo,
    val wind: Wind,
    val clouds: Clouds,
    val name: String
){
    override fun toString(): String {
        return """
            Météo : ${this.weatherInfo}
            Vents : ${this.wind}
            Nuages : ${this.clouds.all}
        """.trimIndent()
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


