package com.elliot.weathermate.data

import android.content.Context
import com.elliot.weathermate.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //Météo du jour
    @GET("weather?appid=6a9643e3d9186f5638171fa46fa26bb8&lang=fr")
    fun getWeather(@Query("q",)city: String,@Query("units")units: String = Utils.units!!): Call<WeatherData>

    @GET("weather?appid=6a9643e3d9186f5638171fa46fa26bb8&lang=fr")
    fun getWeather(@Query("lat")latitude: Float,@Query("lon")lon: Float,@Query("units")units: String = Utils.units!!): Call<WeatherData>

    //Météo de la semaine
    @GET("forecast?appid=6a9643e3d9186f5638171fa46fa26bb8&lang=fr")
    fun getForecast(@Query("q")city: String, @Query("units")units: String = Utils.units!!): Call<List<WeatherData>>
}

object WeatherAPIService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Création d'un objet ApiService
    private val weatherService = retrofit.create(WeatherAPI::class.java)

    // Obtenir la météo courante
    fun getWeather(city: String,onResult: (WeatherData) -> Unit,onFail:(Throwable) ->Unit){
        weatherService.getWeather(city)
            .enqueue(object : Callback<WeatherData> {
                //TODO Fix when not found
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    onResult(response.body()!!)
                }
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    onFail(t)
                }
            })
    }

    // Obtenir la météo ocurante via des coordonnées GPS
    fun getWeatherByCoords(
        lat: Float,
        lon: Float,
        onResult: (WeatherData) -> Unit,
        onFail:(Throwable) ->Unit){
        weatherService.getWeather(lat,lon)
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    onResult(response.body()!!)
                }
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    onFail(t)
                }
            })
    }

    // Cette fonction met à jour les données météorologique d'une géolocalisation
    fun updateWeather(weatherData: WeatherData,){
        weatherData.let {
            if (it.isGPS){
                getWeatherByCoords(it.coord.lat,it.coord.lon,{

                },{})
            }
        }
    }
}