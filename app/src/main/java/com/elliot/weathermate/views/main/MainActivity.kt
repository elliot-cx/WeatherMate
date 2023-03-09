package com.elliot.weathermate.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.elliot.weathermate.R
import com.elliot.weathermate.data.WeatherAPIService.weatherService
import com.elliot.weathermate.data.WeatherData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weathers: MutableList<WeatherData> = mutableListOf()
        recycleView.layoutManager = LinearLayoutManager(this)
        val adapter = WeatherAdapter(weathers)
        //adapter.setClickListener(this)
        recycleView.adapter = adapter

        weatherService.getWeather("Villeurbanne")
            .enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val weatherData: WeatherData = response.body()!!
                weathers.add(weatherData)
                adapter.notifyItemInserted(weathers.indexOf(weatherData))
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("API", t.message.toString())
            }
        })

        weatherService.getWeather("Vienne")
            .enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val weatherData: WeatherData = response.body()!!
                weathers.add(weatherData)
                adapter.notifyItemInserted(weathers.indexOf(weatherData))
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("API", t.message.toString())
            }
        })



    }
}