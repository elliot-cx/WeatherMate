package com.elliot.weathermate

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Utils {


    companion object {



        // Fonction pour sauvegarder les données
        fun saveConfig(data: Boolean, context: Context) {
            val sharedPreferences = context.getSharedPreferences("setup", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("setup", data)
            editor.apply()
        }

        // Fonction pour charger les données
        fun isConfigured(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences("setup", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("setup", false)
        }
    }
}