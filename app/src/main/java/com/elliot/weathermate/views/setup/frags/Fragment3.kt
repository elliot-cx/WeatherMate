package com.elliot.weathermate.views.setup.frags

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elliot.weathermate.R
import com.elliot.weathermate.views.setup.SetupActivity
import com.tayfuncesur.stepper.Stepper
import kotlinx.android.synthetic.main.fragment1.*
import kotlinx.android.synthetic.main.fragment3.*

class Fragment3 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val setupActivity = activity as SetupActivity
        authoriseButton.setOnClickListener{// locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            setupActivity.requestLocationPermission()
            setupActivity.permissionChanged = {
                if (it){
                    authoriseButton.text = "Suivant"
                    authoriseButton.setOnClickListener {
                        //initialiser l'application
                    }
                }
            }
        }
    }

}