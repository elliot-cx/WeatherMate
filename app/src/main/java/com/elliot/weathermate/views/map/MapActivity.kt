package com.elliot.weathermate.views.map

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.elliot.weathermate.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Get a handle to the fragment and register the callback.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Ajoute un marqueur en 0.0, 0.0
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }

}