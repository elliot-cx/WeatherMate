package com.elliot.weathermate.data

data class Geocode(
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String
){
    override fun toString(): String {
        return "${this.name},${this.state},${this.country}"
    }
}
