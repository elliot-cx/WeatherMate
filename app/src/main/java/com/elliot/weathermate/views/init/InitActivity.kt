package com.elliot.weathermate.views.init

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elliot.weathermate.Utils
import com.elliot.weathermate.views.main.MainActivity
import com.elliot.weathermate.views.setup.SetupActivity

class InitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Utils.initConfig(applicationContext)){
            // Initialisation des locations
            Utils.loadLocations(this)
            // Lancement de l'activitée principale
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            startActivity(Intent(this,SetupActivity::class.java))
        }
        this.finish()
    }
}