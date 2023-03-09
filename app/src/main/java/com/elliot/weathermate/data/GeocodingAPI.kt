package com.elliot.weathermate.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingAPI {

    // Rechercher une ville
    @GET("direct?appid=6a9643e3d9186f5638171fa46fa26bb8&limit=5")
    fun getCity(@Query("q")city: String): Call<ArrayList<Geocode>>

    // Rechercher une ville par coordonnées
    @GET("reverse?appid=6a9643e3d9186f5638171fa46fa26bb8")
    fun getCity(@Query("lat")latitude: Float, @Query("lon")lon: Float): Call<WeatherData>

}

object GeocodingAPIService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/geo/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Création d'un objet ApiService
    var geoService = retrofit.create(GeocodingAPI::class.java)
}