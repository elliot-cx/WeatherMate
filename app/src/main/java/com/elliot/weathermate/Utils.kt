package com.elliot.weathermate

import android.content.Context
import com.elliot.weathermate.data.WeatherData

class Utils {


    companion object {

        var units: String? = null
        var weathers: MutableList<WeatherData> = mutableListOf()


        // Fonction pour sauvegarder les données
        fun saveConfig(context: Context) {
            val sharedPreferences = context.getSharedPreferences("units", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("units", units)
            editor.apply()
        }

        // Fonction pour charger les données
        fun initConfig(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences("setup", Context.MODE_PRIVATE)
            units = sharedPreferences.getString("units",null)

            if (units != null){
                return true
            }
            return false
        }

        fun initLocations(context: Context){

        }

    }
}