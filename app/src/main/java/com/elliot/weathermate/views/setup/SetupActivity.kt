package com.elliot.weathermate.views.setup

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.elliot.weathermate.R
import com.google.android.gms.location.*

class SetupActivity : AppCompatActivity() {
    private val locationPermissionCode = 2
    var permissionChanged: (permitted: Boolean) -> Unit = {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
    }

    fun requestLocationPermission(): Boolean{
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
            return false
        }else{
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionChanged(true)
            } else {
                permissionChanged(false)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(locationCallback: (Location) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
    }

    fun setAnimationByName(animationView: LottieAnimationView, name: String){
        animationView.let {
            it.setAnimation(resources.getIdentifier(name, "raw", packageName))
            it.playAnimation()
        }

    }
}