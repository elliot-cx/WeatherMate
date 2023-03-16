package com.elliot.weathermate.views.setup

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.views.main.MainActivity

class SetupActivity : AppCompatActivity() {
    var permissionChanged: (permitted: Boolean) -> Unit = {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionChanged(true)
            } else {
                permissionChanged(false)
            }
        }
    }

    fun setAnimationByName(animationView: LottieAnimationView, name: String){
        animationView.let {
            it.setAnimation(resources.getIdentifier(name, "raw", packageName))
            it.playAnimation()
        }
    }

    fun finishConfig(){
        startActivity(Intent(this, MainActivity::class.java))
        Utils.saveConfig(this)
        Utils.saveLocations(this)
        this.finish()
    }
}