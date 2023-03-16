package com.elliot.weathermate.views.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.WeatherData
import com.elliot.weathermate.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val weatherDataInput = intent.getParcelableExtra<WeatherData>("weather")

        if(Utils.weathers.contains(weatherDataInput)){
            switch1.isChecked = true
        }

        if(weatherDataInput!!.isGPS){
            switch1.isEnabled = false
        }

        switch1.setOnCheckedChangeListener{_,isChecked ->
            if (isChecked){
                Utils.weathers.add(weatherDataInput)
            }else{
                Utils.weathers.remove(weatherDataInput)
            }
            Utils.saveLocations(this)
        }
    }
}