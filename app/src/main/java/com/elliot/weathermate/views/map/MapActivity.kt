package com.elliot.weathermate.views.map

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.data.WeatherAPIService
import com.elliot.weathermate.views.detail.DetailActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Get a handle to the fragment and register the callback.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment!!.getMapAsync { googleMap ->
            // When map is loaded

            googleMap.clear()
            Utils.weathers.forEach{
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(it.coord.lat.toDouble(), it.coord.lon.toDouble()))
                        .title(it.name)
                )
                if (it.isGPS){
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.coord.lat.toDouble(),
                                it.coord.lon.toDouble()),
                            12.0f))
                }
            }

            googleMap.setOnMapClickListener { latLng -> //
                WeatherAPIService.getWeatherByCoords(
                    latLng.latitude.toFloat(),
                    latLng.longitude.toFloat(),
                    {
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("weather",it)
                        startActivity(intent)
                        finish()
                    },
                    {
                        Toast.makeText(
                            this,
                            "Erreur de connexion...",
                            Toast.LENGTH_LONG).show()
                    }
                )

            }
        }
    }

}