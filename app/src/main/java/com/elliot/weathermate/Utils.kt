package com.elliot.weathermate

import android.content.Context
import android.graphics.Color
import com.elliot.weathermate.data.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Utils {


    companion object {

        var units: String? = null
        var weathers: MutableList<WeatherData> = mutableListOf()


        // Fonction pour sauvegarder les données
        fun saveConfig(context: Context) {
            val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("units", units)
            editor.apply()
        }

        // Fonction pour charger les données
        fun initConfig(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            units = sharedPreferences.getString("units",null)

            if (units != null){
                return true
            }
            return false
        }

        fun initLocations(context: Context){

        }

        fun saveLocations(context: Context){
            val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("weathers", Gson().toJson(weathers))
            editor.apply()
        }

        fun loadLocations(context: Context): MutableList<WeatherData>{
            val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("weathers", null)
            val listType: Type = object : TypeToken<MutableList<WeatherData>>() {}.type
            return if (json == null){
                mutableListOf()
            }else{
                gson.fromJson(json, listType)
            }
        }

        fun choseWeatherBackground(weather: String): IntArray {
            when(weather){
                "rain" -> return intArrayOf(
                    Color.rgb(102, 124, 144),
                    Color.rgb(67, 88, 116))
                "clear" -> return intArrayOf(
                    Color.rgb(33, 212, 253),
                    Color.rgb(183, 33, 255))
                "snow" -> return intArrayOf(
                    Color.rgb(105, 179, 250),
                    Color.rgb(52, 124, 227))
                "clouds" -> return intArrayOf(
                    Color.rgb(102, 124, 144),
                    Color.rgb(67, 88, 116))
                else -> return intArrayOf(
                    Color.rgb(102, 124, 144),
                    Color.rgb(67, 88, 116))
            }
        }

        // Dégradé du dessous : JAUNE -> ROSE

        //"clear" -> return intArrayOf(
        //Color.rgb(251, 218, 97),
        //Color.rgb(255, 90, 205))

    }
}