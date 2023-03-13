package com.elliot.weathermate.views.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elliot.weathermate.R
import com.elliot.weathermate.data.WeatherData

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val weatherDataInput = intent.getParcelableExtra<WeatherData>("weather")

        Log.i("TEST", weatherDataInput.toString())
    }
}