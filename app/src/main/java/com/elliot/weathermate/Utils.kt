package com.elliot.weathermate

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.elliot.weathermate.data.WeatherData
import com.elliot.weathermate.views.setup.SetupActivity
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Utils {


    companion object {
        const val locationPermissionCode = 2
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

        fun saveLocations(context: Context){
            val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("weathers", Gson().toJson(weathers))
            editor.apply()
        }

        fun loadLocations(context: Context){
            val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("weathers", null)
            val listType: Type = object : TypeToken<MutableList<WeatherData>>() {}.type
            if (json == null){
                weathers = mutableListOf()
            }else{
                weathers = gson.fromJson(json, listType)
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


        // Geo Part

        // Permet de vérifier si l'utilisateur a autorisé ou non l'accès à la géolocalisation
        fun checkPerms(context: Context):Boolean{
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
        }

        // Permet de demander la permission de localisation
        fun requestLocationPermission(context: Context, activity: Activity): Boolean{
            if (!checkPerms(context)){
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
                )
                return false
            }
            return true
        }

        @SuppressLint("MissingPermission")
        fun getLocation(context: Context, activity: Activity,locationCallback: (Location) -> Unit) {
            if (checkPerms(context)){
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
                val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(5000)
                    .setMaxUpdateDelayMillis(5000)
                    .build()
                fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        p0.lastLocation?.let { location ->
                            locationCallback(location)
                            fusedLocationClient.removeLocationUpdates(this)
                        }
                    }
                }, Looper.getMainLooper())
            }else{
                // TODO
            }
        }
    }
}