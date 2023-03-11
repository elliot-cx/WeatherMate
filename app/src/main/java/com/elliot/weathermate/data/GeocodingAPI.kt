package com.elliot.weathermate.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingAPI {

    // Rechercher une ville
    @GET("direct?appid=6a9643e3d9186f5638171fa46fa26bb8&limit=5")
    fun getGeocodes(@Query("q")city: String): Call<MutableList<Geocode>>

    // Rechercher une ville par coordonnées
    @GET("reverse?appid=6a9643e3d9186f5638171fa46fa26bb8")
    fun getGeocode(@Query("lat")latitude: Float, @Query("lon")lon: Float): Call<Geocode>

}

object GeocodingAPIService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/geo/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Création d'un objet ApiService
    private val geoService: GeocodingAPI = retrofit.create(GeocodingAPI::class.java)

    fun getGeocodes(query: String,onResult: (MutableList<Geocode>) -> Unit,onFail:(Throwable) ->Unit){
        geoService.getGeocodes(query)
            .enqueue(object : Callback<MutableList<Geocode>> {
                override fun onResponse(call: Call<MutableList<Geocode>>, response: Response<MutableList<Geocode>>) {
                    onResult(response.body()!!)
                }
                override fun onFailure(call: Call<MutableList<Geocode>>, t: Throwable) {
                    onFail(t)
                }
            })
    }
}