package com.elliot.weathermate.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //Météo du jour
    @GET("weather?appid=6a9643e3d9186f5638171fa46fa26bb8&units=metric&lang=fr")
    fun getWeather(@Query("q")city: String): Call<WeatherData>

    @GET("weather?appid=6a9643e3d9186f5638171fa46fa26bb8&units=metric&lang=fr")
    fun getWeather(@Query("lat")latitude: Float,@Query("lon")lon: Float): Call<WeatherData>

    //Météo de la semaine
    @GET("forecast?appid=6a9643e3d9186f5638171fa46fa26bb8&units=metric&lang=fr")
    fun getForecast(@Query("q")city: String): Call<List<WeatherData>>
}

object WeatherAPIService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Création d'un objet ApiService
    var weatherService = retrofit.create(WeatherAPI::class.java)
}