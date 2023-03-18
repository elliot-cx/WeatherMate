package com.elliot.weathermate.views.parameters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elliot.weathermate.R
import com.elliot.weathermate.Utils
import com.elliot.weathermate.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.parameters.*
import kotlinx.android.synthetic.main.parameters.metricsButton
import kotlinx.android.synthetic.main.parameters.imperialButton

class ParametersActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parameters)

        // Initialisation du radiobutton coché
        if(Utils.units == "metric") {
            metricsButton.isChecked = true
            imperialButton.isChecked = false
        } else if(Utils.units == "imperial") {
            imperialButton.isChecked = true
            metricsButton.isChecked = false
        }

        // Retour à la page principale
        back_arrow.setOnClickListener {
            // Changement des unités de mesures
            if(metricsButton.isChecked){
                Utils.units = "metric"
            }
            else{
                Utils.units = "imperial"
            }
            Utils.saveConfig(this)
            // Changement d'activity
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}